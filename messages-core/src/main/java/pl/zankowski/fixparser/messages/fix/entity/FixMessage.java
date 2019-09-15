package pl.zankowski.fixparser.messages.fix.entity;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.time.Instant;
import java.util.Objects;

public class FixMessage implements IEntity {

    private Long id;
    private Long userId;
    private Instant timestamp;
    private String input;

    public FixMessage() {
    }

    public FixMessage(final Long id, final Long userId,
            final Instant timestamp, final String input) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.input = input;
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

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(final FixMessage fixMessage) {
        Objects.requireNonNull(fixMessage);
        return new Builder(fixMessage);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixMessage that = (FixMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, timestamp, input);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("timestamp", timestamp)
                .add("input", input)
                .toString();
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Instant timestamp;
        private String input;

        public Builder() {
        }

        public Builder(final FixMessage fixMessage) {
            this.id = fixMessage.getId();
            this.userId = fixMessage.getUserId();
            this.timestamp = fixMessage.getTimestamp();
            this.input = fixMessage.getInput();
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

        public Builder withInput(final String input) {
            this.input = input;
            return this;
        }

        public FixMessage build() {
            return new FixMessage(id, userId, timestamp, input);
        }
    }
}
