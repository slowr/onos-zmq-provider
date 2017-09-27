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

public class createSessionFpc {
    public static String BROADCAST_TOPIC = "0";
    private static byte CREATE_SESSION_TYPE = 0b0000_0001;

    private byte dpn;
    private byte[] imsi;
    private byte[] ueIp;
    private byte lbi;
    private byte[] s1uSgwGtpuIpv4;
    private byte[] s1uSgwGtpuTeid;
    private byte[] clientIdentifier;
    private byte[] opIdentifier;
    private byte[] sessionId;
    private byte controllerTopic;

    private createSessionFpc(byte dpn, byte[] imsi, byte[] ueIp, byte lbi, byte[] s1uSgwGtpuIpv4,
                             byte[] s1uSgwGtpuTeid, byte[] clientIdentifier, byte[] opIdentifier,
                             byte[] sessionId, byte controllerTopic) {
        this.dpn = dpn;
        this.imsi = imsi;
        this.ueIp = ueIp;
        this.lbi = lbi;
        this.s1uSgwGtpuIpv4 = s1uSgwGtpuIpv4;
        this.s1uSgwGtpuTeid = s1uSgwGtpuTeid;
        this.clientIdentifier = clientIdentifier;
        this.opIdentifier = opIdentifier;
        this.sessionId = sessionId;
        this.controllerTopic = controllerTopic;
    }

    ByteBuffer getBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(41);
        bb.put(dpn)
                .put(CREATE_SESSION_TYPE)
                .put(imsi)
                .put(lbi)
                .put(ueIp)
                .put(s1uSgwGtpuTeid)
                .put(s1uSgwGtpuIpv4)
                .put(sessionId)
                .put(controllerTopic)
                .put(clientIdentifier)
                .put(opIdentifier);
        return bb;
    }

    public static class Builder {
        private Short dpn;
        private BigInteger imsi;
        private Ip4Address ueIp;
        private Short lbi;
        private Ip4Address s1uSgwGtpuIpv4;
        private Long s1uSgwGtpuTeid;
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

        public Builder imsi(BigInteger imsi) {
            this.imsi = imsi;
            return this;
        }

        public Builder ueIp(Ip4Address ueIp) {
            this.ueIp = ueIp;
            return this;
        }

        public Builder defaultEbi(Short defaultEbi) {
            this.lbi = defaultEbi;
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

        public createSessionFpc build() {
            checkNotNull(dpn);
            checkNotNull(imsi);
            checkNotNull(ueIp);
            checkNotNull(lbi);
            checkNotNull(s1uSgwGtpuIpv4);
            checkNotNull(s1uSgwGtpuTeid);
            checkNotNull(clientIdentifier);
            checkNotNull(opIdentifier);
            checkNotNull(sessionId);

            return new createSessionFpc(
                    toUint8(dpn),
                    toUint64(imsi),
                    toUint32(ueIp.toInt()),
                    toUint8(lbi),
                    toUint32(s1uSgwGtpuIpv4.toInt()),
                    toUint32(s1uSgwGtpuTeid.intValue()),
                    toUint32(clientIdentifier.intValue()),
                    toUint32(opIdentifier.intValue()),
                    toUint64(BigInteger.valueOf(sessionId)),
                    // FIXME: change topic?
                    toUint8(Short.valueOf(BROADCAST_TOPIC))
            );
        }
    }
}
