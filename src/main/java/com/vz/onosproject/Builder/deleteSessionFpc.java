/*
 * Copyright 2015-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vz.onosproject.Builder;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkNotNull;

public class deleteSessionFpc {
    public static String BROADCAST_TOPIC = "0";
    private static byte DELETE_SESSION_TYPE = 0b0000_0011;
    private byte dpn;
    private byte[] clientIdentifier;
    private byte[] opIdentifier;
    private byte[] sessionId;
    private byte controllerTopic;

    public deleteSessionFpc(byte dpn, byte[] clientIdentifier, byte[] opIdentifier, byte[] sessionId, byte controllerTopic) {
        this.dpn = dpn;
        this.clientIdentifier = clientIdentifier;
        this.opIdentifier = opIdentifier;
        this.sessionId = sessionId;
        this.controllerTopic = controllerTopic;
    }

    public ByteBuffer getBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(19);
        bb.put(dpn)
                .put(DELETE_SESSION_TYPE)
                .put(sessionId)
                .put(controllerTopic)
                .put(clientIdentifier)
                .put(opIdentifier);
        return bb;
    }

    public static class Builder {
        private Short dpn;
        private Long clientIdentifier;
        private BigInteger opIdentifier;
        private Long sessionId;

        protected Builder() {

        }

        private static byte toUint8(Short value) {
            return value.byteValue();
        }

        private static byte[] toUint16(Short value) {
            return new byte[]{(byte) (value >>> 8), (byte) (value & 0xFF)};
        }

        private static byte[] toUint32(int value) {
            return new byte[]{(byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) (value & 0xFF)};
        }

        private static byte[] toUint64(BigInteger value) {
            return new byte[]{value.shiftRight(56).byteValue(), value.shiftRight(48).byteValue(), value.shiftRight(40).byteValue(),
                    value.shiftRight(32).byteValue(), value.shiftRight(24).byteValue(), value.shiftRight(16).byteValue(),
                    value.shiftRight(8).byteValue(), value.and(BigInteger.valueOf(0xFF)).byteValue()};
        }

        public Builder dpn(Short dpn) {
            this.dpn = dpn;
            return this;
        }

        public Builder clientIdentifier(Long clientIdentifier) {
            this.clientIdentifier = clientIdentifier;
            return this;
        }

        public Builder opIdentifier(BigInteger opIdentifier) {
            this.opIdentifier = opIdentifier;
            return this;
        }

        public Builder sessionId(Long sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public deleteSessionFpc build() {
            checkNotNull(dpn);
            checkNotNull(clientIdentifier);
            checkNotNull(opIdentifier);
            checkNotNull(sessionId);

            return new deleteSessionFpc(
                    toUint8(dpn),
                    toUint32(clientIdentifier.intValue()),
                    toUint32(opIdentifier.intValue()),
                    toUint64(BigInteger.valueOf(sessionId)),
                    // FIXME: change topic?
                    toUint8(Short.valueOf(BROADCAST_TOPIC))
            );
        }
    }
}
