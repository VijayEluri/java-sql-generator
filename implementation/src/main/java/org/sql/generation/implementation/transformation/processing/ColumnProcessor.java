/*
 * Copyright (c) 2010, Stanislav Muhametsin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.sql.generation.implementation.transformation.processing;

import java.util.Iterator;

import org.sql.generation.api.grammar.common.ColumnNameList;
import org.sql.generation.api.grammar.common.SQLConstants;
import org.sql.generation.api.grammar.query.ColumnReference;
import org.sql.generation.api.grammar.query.ColumnReferenceByExpression;
import org.sql.generation.api.grammar.query.ColumnReferenceByName;
import org.sql.generation.implementation.transformation.processing.general.SQLProcessorAggregator;

/**
 * Currently not thread-safe.
 * 
 * @author Stanislav Muhametsin
 */
public class ColumnProcessor
{

    public static class ColumnReferenceByNameProcessor extends
        AbstractProcessor<ColumnReference, ColumnReferenceByName>
    {
        public ColumnReferenceByNameProcessor()
        {
            super( ColumnReferenceByName.class );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, ColumnReferenceByName columnRef,
            StringBuilder builder )
        {
            String tableName = columnRef.getTableName();
            if( ProcessorUtils.notNullAndNotEmpty( tableName ) )
            {
                builder.append( tableName ).append( SQLConstants.TABLE_COLUMN_SEPARATOR );
            }

            builder.append( columnRef.getColumnName() );
        }
    }

    public static class ColumnReferenceByExpressionProcessor extends
        AbstractProcessor<ColumnReference, ColumnReferenceByExpression>
    {

        public ColumnReferenceByExpressionProcessor()
        {
            super( ColumnReferenceByExpression.class );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, ColumnReferenceByExpression columnRef,
            StringBuilder builder )
        {
            processor.process( columnRef.getExpression(), builder );
        }
    }

    public static class ColumnNamesProcessor extends AbstractProcessor<ColumnNameList, ColumnNameList>
    {
        public ColumnNamesProcessor()
        {
            super( ColumnNameList.class );
        }

        @Override
        protected void doProcess( SQLProcessorAggregator processor, ColumnNameList object, StringBuilder builder )
        {
            Iterator<String> iter = object.getColumnNames().iterator();
            while( iter.hasNext() )
            {
                builder.append( iter.next() );
                if( iter.hasNext() )
                {
                    builder.append( SQLConstants.COMMA ).append( SQLConstants.TOKEN_SEPARATOR );
                }
            }
        }
    }
}