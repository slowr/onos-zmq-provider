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

import org.onlab.packet.Ip4Address;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static com.google.common.base.Preconditions.checkNotNull;

public class modifyBearerDlFpc {
    private static byte MODIFY_DL_BEARER_TYPE = 0b0000_0010;

    private byte dpn;
    private byte[] s1uSgwGtpuTeid;

    private byte[] s1uEnbGtpuIpv4;
    private byte[] s1uEnbGtpuTeid;

    private byte[] clientIdentifier;
    private byte[] opIdentifier;

    private modifyBearerDlFpc(byte dpn, byte[] s1uSgwGtpuTeid, byte[] s1uEnbGtpuIpv4, byte[] s1uEnbGtpuTeid,
                              byte[] clientIdentifier, byte[] opIdentifier) {
        this.dpn = dpn;
        this.s1uSgwGtpuTeid = s1uSgwGtpuTeid;
        this.s1uEnbGtpuIpv4 = s1uEnbGtpuIpv4;
        this.s1uEnbGtpuTeid = s1uEnbGtpuTeid;
        this.clientIdentifier = clientIdentifier;
        this.opIdentifier = opIdentifier;
    }

    public ByteBuffer getBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(23);
        bb.put(dpn)
                .put(MODIFY_DL_BEARER_TYPE)
                .put(s1uEnbGtpuIpv4)
                .put(s1uEnbGtpuTeid)
                .put(s1uSgwGtpuTeid)
                .put(clientIdentifier)
                .put(opIdentifier);
        return bb;
    }

    public static class Builder {
        private Short dpn;
        private Long s1uSgwGtpuTeid;
        private Long clientIdentifier;
        private BigInteger opIdentifier;
        private Ip4Address s1uEnbGtpuIpv4;
        private Long s1uEnbGtpuTeid;

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

        public Builder s1uSgwGtpuTeid(Long s1uSgwGtpuTeid) {
            this.s1uSgwGtpuTeid = s1uSgwGtpuTeid;
            return this;
        }

        public Builder s1uEnbGtpuIpv4(Ip4Address s1uEnbGtpuIpv4) {
            this.s1uEnbGtpuIpv4 = s1uEnbGtpuIpv4;
            return this;
        }

        public Builder s1uEnbGtpuTeid(Long s1uEnbGtpuTeid) {
            this.s1uEnbGtpuTeid = s1uEnbGtpuTeid;
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

        public modifyBearerDlFpc build() {
            checkNotNull(dpn);
            checkNotNull(s1uEnbGtpuIpv4);
            checkNotNull(s1uEnbGtpuTeid);
            checkNotNull(clientIdentifier);
            checkNotNull(opIdentifier);

            return new modifyBearerDlFpc(
                    toUint8(dpn),
                    toUint32(s1uSgwGtpuTeid.intValue()),
                    toUint32(s1uEnbGtpuIpv4.toInt()),
                    toUint32(s1uEnbGtpuTeid.intValue()),
                    toUint32(clientIdentifier.intValue()),
                    toUint32(opIdentifier.intValue())
            );
        }
    }
}
