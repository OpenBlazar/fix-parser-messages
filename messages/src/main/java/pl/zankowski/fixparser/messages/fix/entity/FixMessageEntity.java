package pl.zankowski.fixparser.messages.fix.entity;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.time.Instant;
import java.util.Objects;

public class FixMessageEntity implements IEntity {

    private Long id;
    private Long userId;
    private Instant timestamp;
    private String message;

    public FixMessageEntity() {
    }

    public FixMessageEntity(final Long id, final Long userId,
            final Instant timestamp, final String message) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final FixMessageEntity fixMessage) {
        Objects.requireNonNull(fixMessage);
        return new Builder(fixMessage);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixMessageEntity that = (FixMessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, timestamp, message);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("timestamp", timestamp)
                .add("input", message)
                .toString();
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Instant timestamp;
        private String message;

        public Builder() {
        }

        public Builder(final FixMessageEntity fixMessage) {
            this.id = fixMessage.getId();
            this.userId = fixMessage.getUserId();
            this.timestamp = fixMessage.getTimestamp();
            this.message = fixMessage.getMessage();
        }

        public Builder withId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder withUserId(final Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder withTimestamp(final Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withMessage(final String input) {
            this.message = input;
            return this;
        }

        public FixMessageEntity build() {
            return new FixMessageEntity(id, userId, timestamp, message);
        }
    }
}
