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

package org.sql.generation.implementation.grammar.query.joins;

import org.sql.generation.api.common.NullArgumentException;
import org.sql.generation.api.grammar.query.QueryExpressionBody;
import org.sql.generation.api.grammar.query.TableReference;
import org.sql.generation.api.grammar.query.joins.JoinedTable;
import org.sql.generation.implementation.grammar.query.TableReferenceImpl;

/**
 * 
 * @author Stanislav Muhametsin
 */
public abstract class JoinedTableImpl<TableReferenceType extends JoinedTable> extends
    TableReferenceImpl<QueryExpressionBody, TableReferenceType>
    implements JoinedTable
{

    private final TableReference _left;

    protected JoinedTableImpl( Class<? extends TableReferenceType> tableReferenceClass, TableReference left )
    {
        super( tableReferenceClass );
        NullArgumentException.validateNotNull( "left", left );

        this._left = left;
    }

    public TableReference getLeft()
    {
        return this._left;
    }

}