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

package org.sql.generation.implementation.vendor;

import org.lwdci.api.context.EmptyExecutionArgs;
import org.sql.generation.implementation.transformation.processing.SQLStatementProcessor;
import org.sql.generation.implementation.transformation.spi.SQLTransformationContext;
import org.sql.generation.implementation.transformation.spi.SQLTransformationContextCreationArgs;

/**
 * 
 * @author Stanislav Muhametsin
 */
public class DefaultVendorContext extends SQLTransformationContext
{

    public DefaultVendorContext( SQLTransformationContextCreationArgs args )
    {
        super( args );
    }

    @Override
    protected String doInteraction( EmptyExecutionArgs args )
        throws RuntimeException
    {
        StringBuilder builder = new StringBuilder();
        SQLStatementProcessor processor = this.getProcessor();
        processor.processStatement( this.getArgs().getSQLStatement(), builder );
        return builder.toString();
    }

    protected SQLStatementProcessor getProcessor()
    {
        return new SQLStatementProcessor();
    }
}