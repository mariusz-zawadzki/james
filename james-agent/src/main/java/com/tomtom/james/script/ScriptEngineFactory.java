/*
 * Copyright 2017 TomTom International B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtom.james.script;

import com.tomtom.james.agent.ToolkitManager;
import com.tomtom.james.common.api.publisher.EventPublisher;
import com.tomtom.james.common.api.script.ScriptEngine;
import com.tomtom.james.configuration.AgentConfiguration;
import com.tomtom.james.informationpoint.advice.ScriptEngineSupplier;

public class ScriptEngineFactory {

    private ScriptEngineFactory() {
    }

    public static ScriptEngine create(EventPublisher publisher,
                                      AgentConfiguration configuration,
                                      ToolkitManager toolkitManager) {
        ScriptEngine engine = new DisruptorAsyncScriptEngine(
                    new GroovyScriptEngine(publisher, toolkitManager),
                    configuration.getScriptEngineConfiguration().getAsyncWorkers(),
                    configuration.getScriptEngineConfiguration().getMaxAsyncJobQueueCapacity());
        ScriptEngineSupplier.register(engine);
        return engine;
    }
}
