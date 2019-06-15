package de.ls5.wt2.conf.rest;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonBProvider implements ContextResolver<Jsonb> {

    final Jsonb builder;

    public JsonBProvider() {
        final JsonbConfig config = new JsonbConfig().withFormatting(true);

        this.builder = JsonbBuilder.create(config);
    }

    @Override
    public Jsonb getContext(Class<?> type) {
        return builder;
    }
}
