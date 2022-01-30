package com.pankajkcodes.videodownloader.models;

public class GraphQlModel {
   private ShortcodeMediaModel graphql;

    public GraphQlModel(ShortcodeMediaModel graphql) {
        this.graphql = graphql;
    }

    public ShortcodeMediaModel getGraphql() {
        return graphql;
    }

    public void setGraphql(ShortcodeMediaModel graphql) {
        this.graphql = graphql;
    }
}
