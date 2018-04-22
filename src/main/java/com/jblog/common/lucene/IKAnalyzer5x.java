package com.jblog.common.lucene;

import org.apache.lucene.analysis.Analyzer;

public class IKAnalyzer5x extends Analyzer {

    private boolean useSmart;

    public boolean useSmart() {
        return this.useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public IKAnalyzer5x() {
        this(false);
    }

    public IKAnalyzer5x(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        IKTokenizer5x _IKTokenizer = new IKTokenizer5x(this.useSmart);
        return new TokenStreamComponents(_IKTokenizer);
    }
}
