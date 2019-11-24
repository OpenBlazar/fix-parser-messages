package pl.zankowski.fixparser.messages.dictionary.entity;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Map;
import java.util.Objects;

@Document
public class FixDictionary implements IEntity {

    private static final long serialVersionUID = 7343128776320499665L;

    @Id
    private DictionaryDescriptor dictionaryDescriptor;
    private Map<Integer, FixFieldDefinition> dictionaryMap;

    public FixDictionary() {
    }

    public FixDictionary(final DictionaryDescriptor dictionaryDescriptor,
            final Map<Integer, FixFieldDefinition> dictionaryMap) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        this.dictionaryMap = dictionaryMap;
    }

    public DictionaryDescriptor getDictionaryDescriptor() {
        return dictionaryDescriptor;
    }

    public void setDictionaryDescriptor(
            final DictionaryDescriptor dictionaryDescriptor) {
        this.dictionaryDescriptor = dictionaryDescriptor;
    }

    public Map<Integer, FixFieldDefinition> getDictionaryMap() {
        return dictionaryMap;
    }

    public void setDictionaryMap(
            final Map<Integer, FixFieldDefinition> dictionaryMap) {
        this.dictionaryMap = dictionaryMap;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final FixDictionary fixDictionary) {
        Objects.requireNonNull(fixDictionary);
        return new Builder(fixDictionary);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixDictionary that = (FixDictionary) o;
        return Objects.equals(dictionaryDescriptor, that.dictionaryDescriptor) &&
                Objects.equals(dictionaryMap, that.dictionaryMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionaryDescriptor, dictionaryMap);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dictionaryDescriptor", dictionaryDescriptor)
                .add("dictionaryMap", dictionaryMap)
                .toString();
    }

    public static final class Builder {

        private DictionaryDescriptor dictionaryDescriptor;
        private Map<Integer, FixFieldDefinition> dictionaryMap;

        public Builder() {
        }

        public Builder(final FixDictionary fixDictionary) {
            this.dictionaryDescriptor = fixDictionary.getDictionaryDescriptor();
            this.dictionaryMap = fixDictionary.getDictionaryMap();
        }

        public Builder withDictionaryDescriptorEntity(final DictionaryDescriptor dictionaryDescriptor) {
            this.dictionaryDescriptor = dictionaryDescriptor;
            return this;
        }

        public Builder withDictionaryMap(final Map<Integer, FixFieldDefinition> dictionaryMap) {
            this.dictionaryMap = dictionaryMap;
            return this;
        }

        public FixDictionary build() {
            return new FixDictionary(dictionaryDescriptor, dictionaryMap);
        }
    }

}
