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

public class createBearerUlFpc {
    private static byte CREATE_UL_BEARER_TYPE = 0b0000_0101;

    private byte dpn;
    private byte[] imsi;
    private byte defaultEbi;
    private byte dedicatedEbi;
    private byte[] s1uSgwGtpuIpv4;
    private byte[] s1uSgwGtpuTeid;

    public createBearerUlFpc(byte dpn, byte[] imsi, byte defaultEbi, byte dedicatedEbi,
                             byte[] s1uSgwGtpuIpv4, byte[] s1uSgwGtpuTeid) {
        this.dpn = dpn;
        this.imsi = imsi;
        this.defaultEbi = defaultEbi;
        this.dedicatedEbi = dedicatedEbi;
        this.s1uSgwGtpuIpv4 = s1uSgwGtpuIpv4;
        this.s1uSgwGtpuTeid = s1uSgwGtpuTeid;
    }

    ByteBuffer getBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(21);
        bb.put(dpn)
                .put(CREATE_UL_BEARER_TYPE)
                .put(imsi)
                .put(defaultEbi)
                .put(dedicatedEbi)
                .put(s1uSgwGtpuIpv4)
                .put(s1uSgwGtpuTeid);
        return bb;
    }

    public static class Builder {
        private Short dpn;
        private BigInteger imsi;
        private Short defaultEbi;
        private Short dedicatedEbi;
        private Ip4Address s1uSgwGtpuIpv4;
        private Long s1uSgwGtpuTeid;

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

        public Builder imsi(BigInteger imsi) {
            this.imsi = imsi;
            return this;
        }

        public Builder defaultEbi(Short defaultEbi) {
            this.defaultEbi = defaultEbi;
            return this;
        }

        public Builder s1uSgwGtpuIpv4(Ip4Address s1uSgwGtpuIpv4) {
            this.s1uSgwGtpuIpv4 = s1uSgwGtpuIpv4;
            return this;
        }

        public Builder s1uSgwGtpuTeid(Long s1uSgwGtpuTeid) {
            this.s1uSgwGtpuTeid = s1uSgwGtpuTeid;
            return this;
        }

        public createBearerUlFpc build() {
            checkNotNull(dpn);
            checkNotNull(imsi);
            checkNotNull(defaultEbi);
            checkNotNull(dedicatedEbi);
            checkNotNull(s1uSgwGtpuIpv4);
            checkNotNull(s1uSgwGtpuTeid);

            return new createBearerUlFpc(
                    toUint8(dpn),
                    toUint64(imsi),
                    toUint8(defaultEbi),
                    toUint8(dedicatedEbi),
                    toUint32(s1uSgwGtpuIpv4.toInt()),
                    toUint32(s1uSgwGtpuTeid.intValue())
            );
        }
    }
}
