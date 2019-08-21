package com.tomtom.james.script

class AsyncScriptEngine_fragmentedSpec extends AsyncScriptEngineSpec{
    @Override
    protected int framgentation() {
        return 50;
    }
}
