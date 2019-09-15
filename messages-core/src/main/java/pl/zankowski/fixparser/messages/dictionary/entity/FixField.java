package pl.zankowski.fixparser.messages.dictionary.entity;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Objects;

public class FixField implements IEntity {

    private static final long serialVersionUID = 8784537477337622848L;

    private int tag;
    private String name;

    public FixField() {
    }

    public FixField(final int tag, final String name) {
        this.tag = tag;
        this.name = name;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(final int tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final FixField fixField) {
        Objects.requireNonNull(fixField);
        return new Builder(fixField);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixField that = (FixField) o;
        return tag == that.tag &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("name", name)
                .toString();
    }

    public static final class Builder {
        private int tag;
        private String name;

        public Builder() {
        }

        public Builder(final FixField fixField) {
            this.tag = fixField.getTag();
            this.name = fixField.getName();
        }

        public Builder withTag(final int tag) {
            this.tag = tag;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public FixField build() {
            return new FixField(tag, name);
        }
    }
}
