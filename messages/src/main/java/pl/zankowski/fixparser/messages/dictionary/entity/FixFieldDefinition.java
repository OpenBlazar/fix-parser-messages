package pl.zankowski.fixparser.messages.dictionary.entity;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Map;
import java.util.Objects;

public class FixFieldDefinition implements IEntity {

    private static final long serialVersionUID = 8245712429839352129L;

    private FixField field;
    private Map<String, String> values;

    public FixFieldDefinition() {
    }

    public FixFieldDefinition(final FixField field, final Map<String, String> values) {
        this.field = field;
        this.values = values;
    }

    public FixField getField() {
        return field;
    }

    public void setField(final FixField field) {
        this.field = field;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(final Map<String, String> values) {
        this.values = values;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final FixFieldDefinition fixFieldDefinition) {
        Objects.requireNonNull(fixFieldDefinition);
        return new Builder(fixFieldDefinition);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixFieldDefinition that = (FixFieldDefinition) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("field", field)
                .add("values", values)
                .toString();
    }

    public static final class Builder {
        private FixField field;
        private Map<String, String> values = Maps.newHashMap();

        public Builder() {
        }

        public Builder(final FixFieldDefinition fixFieldDefinition) {
            this.field = fixFieldDefinition.getField();
            this.values = fixFieldDefinition.getValues();
        }

        public Builder withField(final FixField field) {
            this.field = field;
            return this;
        }

        public Builder withValues(final Map<String, String> values) {
            this.values = values;
            return this;
        }

        public Builder withValue(final String name, final String description) {
            this.values.put(name, description);
            return this;
        }

        public FixFieldDefinition build() {
            return new FixFieldDefinition(field, values);
        }
    }
}
