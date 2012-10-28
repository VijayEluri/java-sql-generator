/*
 *  Copyright 2012 Paul Merlin.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.sql.generation.implementation.transformation.sqlite;

import java.util.Map;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.definition.schema.SchemaDefinition;
import org.sql.generation.api.grammar.definition.table.AutoGenerationPolicy;
import org.sql.generation.api.grammar.definition.table.ColumnDefinition;
import org.sql.generation.api.grammar.definition.table.TableConstraint;
import org.sql.generation.api.grammar.definition.table.TableConstraintDefinition;
import org.sql.generation.api.grammar.definition.table.TableElement;
import org.sql.generation.api.grammar.definition.table.UniqueConstraint;
import org.sql.generation.api.grammar.definition.table.UniqueSpecification;
import org.sql.generation.implementation.transformation.DefinitionProcessing.ColumnDefinitionProcessor;
import org.sql.generation.implementation.transformation.DefinitionProcessing.SchemaDefinitionProcessor;
import org.sql.generation.implementation.transformation.DefinitionProcessing.TableElementListProcessor;
import org.sql.generation.implementation.transformation.spi.SQLProcessorAggregator;

public class DefinitionProcessing
{

    public static class SQLListeTableElementListProcessor
            extends TableElementListProcessor
    {

        @Override
        protected void processTableElement( SQLProcessorAggregator aggregator, TableElement object,
                StringBuilder builder, boolean hasNext )
        {
            if( object.getImplementedType().isAssignableFrom( TableConstraintDefinition.class ) )
            {
                TableConstraint constraint =
                    TableConstraintDefinition.class.cast( object ).getConstraint();
                if( constraint.getImplementedType().isAssignableFrom( UniqueConstraint.class ) )
                {
                    UniqueConstraint unique = UniqueConstraint.class.cast( constraint );
                    if( UniqueSpecification.PRIMARY_KEY.equals( unique.getUniquenessKind() ) )
                    {
                        return; // Skip the whole TableElement for SQL autogen support, see below
                    }
                }

            }
            super.processTableElement( aggregator, object, builder, hasNext );
        }

    }

    public static class SQLiteSchemaDefinitionProcessor
            extends SchemaDefinitionProcessor
    {

        @Override
        protected void doProcess( SQLProcessorAggregator aggregator, SchemaDefinition object,
                StringBuilder builder )
        {
            // Just process schema elements
            this.processSchemaElements( aggregator, object, builder );
        }

    }

    public static class SQLiteColumnDefinitionProcessor
            extends ColumnDefinitionProcessor
    {

        private final Map<Class<?>, String> _autoGenDataTypes;

        public SQLiteColumnDefinitionProcessor( Map<Class<?>, String> autoGenDataTypes )
        {
            NullArgumentException.validateNotNull( "Data type serial names", autoGenDataTypes );
            this._autoGenDataTypes = autoGenDataTypes;
        }

        @Override
        protected void processDataType( SQLProcessorAggregator aggregator, ColumnDefinition object,
                StringBuilder builder )
        {
            AutoGenerationPolicy autoGenPolicy = object.getAutoGenerationPolicy();
            if( autoGenPolicy == null )
            {
                super.processDataType( aggregator, object, builder );
            }
            else
            {
                // SQLite can't handle the ALWAYS strategy
                if( AutoGenerationPolicy.BY_DEFAULT.equals( autoGenPolicy ) )
                {
                    // SQLite MUST use INTEGER type when autogenerated
                    Class<?> dtClass = object.getDataType().getClass();
                    Boolean success = false;
                    for( Map.Entry<Class<?>, String> entry : this._autoGenDataTypes.entrySet() )
                    {
                        success = entry.getKey().isAssignableFrom( dtClass );
                        if( success )
                        {
                            builder.append( entry.getValue() );
                            break;
                        }
                    }
                    if( !success )
                    {
                        throw new UnsupportedOperationException( "Unsupported column data type "
                                + object.getDataType()
                                + " for auto-generated column." );
                    }
                }
                else
                {
                    throw new UnsupportedOperationException( "Unsupported auto generation policy: "
                            + autoGenPolicy
                            + "." );
                }
            }
        }

        @Override
        protected void processMayBeNull( ColumnDefinition object, StringBuilder builder )
        {
            // Nothing to do - auto generation policy handled in data type orc
        }

        @Override
        protected void processAutoGenerationPolicy( ColumnDefinition object, StringBuilder builder )
        {
            // Nothing to do - auto generation policy handled in data type orc
        }

    }

}
