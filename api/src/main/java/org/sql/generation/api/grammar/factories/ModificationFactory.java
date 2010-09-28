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

package org.sql.generation.api.grammar.factories;

import org.sql.generation.api.grammar.builders.modification.ColumnSourceByValuesBuilder;
import org.sql.generation.api.grammar.builders.modification.DeleteBySearchBuilder;
import org.sql.generation.api.grammar.builders.modification.InsertStatementBuilder;
import org.sql.generation.api.grammar.builders.modification.UpdateBySearchBuilder;
import org.sql.generation.api.grammar.common.ColumnNameList;
import org.sql.generation.api.grammar.common.TableName;
import org.sql.generation.api.grammar.common.ValueExpression;
import org.sql.generation.api.grammar.modification.ColumnSourceByQuery;
import org.sql.generation.api.grammar.modification.SetClause;
import org.sql.generation.api.grammar.modification.TargetTable;
import org.sql.generation.api.grammar.modification.UpdateSource;
import org.sql.generation.api.grammar.modification.UpdateSourceByExpression;
import org.sql.generation.api.grammar.query.QueryExpression;

/**
 * 
 * @author Stanislav Muhametsin
 */
public interface ModificationFactory
{

    public ColumnSourceByValuesBuilder columnSourceByValues();

    public ColumnSourceByQuery columnSourceByQuery( QueryExpression query );

    public ColumnSourceByQuery columnSourceByQuery( ColumnNameList columnNames, QueryExpression query );

    public DeleteBySearchBuilder deleteBySearch();

    public InsertStatementBuilder insert();

    public UpdateBySearchBuilder updateBySearch();

    public TargetTable createTargetTable( TableName tableName );

    public TargetTable createTargetTable( TableName tableName, Boolean isOnly );

    public UpdateSourceByExpression updateSourceByExp( ValueExpression expression );

    public SetClause setClause( String updateTarget, UpdateSource updateSource );

}