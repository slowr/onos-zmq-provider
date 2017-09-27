/*
 * Copyright 2017 Verizon
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

public class DefaultFpc {
    public static String BROADCAST_TOPIC = "0";
    private static byte MODIFY_UL_BEARER_TYPE = 0b0000_0100;
    private static byte CREATE_UL_BEARER_TYPE = 0b0000_0101;
    private static byte CREATE_DL_BEARER_TYPE = 0b0000_0110;
    private static byte DELETE_BEARER_TYPE = 0b0000_0110;
    private static byte HELLO = 0b0000_1000;
    private static byte BYE = 0b0000_1001;

    private byte dpn;
    private byte[] imsi;
    private byte[] ueIp;
    private byte lbi;
    private byte[] s1uSgwGtpuIpv4;
    private byte[] s1uSgwGtpuTeid;

    private byte[] s1uEnbGtpuIpv4;
    private byte[] s1uEnbGtpuTeid;

    private byte[] clientIdentifier;
    private byte[] opIdentifier;
    private byte[] sessionId;
    private byte controllerTopic;


    public ByteBuffer delete_session() {
        ByteBuffer bb = ByteBuffer.allocate(19);

        return bb;
    }

}
