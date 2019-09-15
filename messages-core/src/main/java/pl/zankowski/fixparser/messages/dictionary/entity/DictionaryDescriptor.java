package pl.zankowski.fixparser.messages.dictionary.entity;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;

import java.util.Objects;

public class DictionaryDescriptor implements IEntity {

    private static final long serialVersionUID = -1878360863772226497L;

    private Long userId;
    private String providerName;
    private DictionaryLoaderType loaderType;

    public DictionaryDescriptor() {
    }

    public DictionaryDescriptor(
            final Long userId,
            final String providerName,
            final DictionaryLoaderType loaderType) {
        this.userId = userId;
        this.providerName = providerName;
        this.loaderType = loaderType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(final String providerName) {
        this.providerName = providerName;
    }

    public DictionaryLoaderType getLoaderType() {
        return loaderType;
    }

    public void setLoaderType(final DictionaryLoaderType loaderType) {
        this.loaderType = loaderType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final DictionaryDescriptor dictionaryDescriptor) {
        Objects.requireNonNull(dictionaryDescriptor);
        return new Builder(dictionaryDescriptor);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DictionaryDescriptor that = (DictionaryDescriptor) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(providerName, that.providerName) &&
                loaderType == that.loaderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, providerName, loaderType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("providerName", providerName)
                .add("loaderType", loaderType)
                .toString();
    }

    public static final class Builder {
        private Long userId;
        private String providerName;
        private DictionaryLoaderType loaderType;

        public Builder() {
        }

        public Builder(final DictionaryDescriptor dictionaryDescriptor) {
            this.userId = dictionaryDescriptor.getUserId();
            this.providerName = dictionaryDescriptor.getProviderName();
            this.loaderType = dictionaryDescriptor.getLoaderType();
        }

        public Builder withUserId(final Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder withProviderName(final String providerName) {
            this.providerName = providerName;
            return this;
        }

        public Builder withLoaderType(final DictionaryLoaderType loaderType) {
            this.loaderType = loaderType;
            return this;
        }

        public DictionaryDescriptor build() {
            return new DictionaryDescriptor(userId, providerName, loaderType);
        }
    }

}
