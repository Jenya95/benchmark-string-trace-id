/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(jvmArgs = {"-Xms2G", "-Xmx2G"})
public class MyBenchmark {
    private static final String CORR_ID = "[520a669a-4d4e-4757-aba3-5d90bdd84197]";
    private static final String json = "Response from server:\n" +
                                       "Rq url: GET /pcs/v1/users/test-qa-456/documents/subfolders\n" +
                                       "Rs headers: [ap-correlation-id:\"[520a669a-4d4e-4757-aba3-5d90bdd84197]\"]\n" +
                                       "Rs body:\n" +
                                       "{\n" +
                                       "  \"folderList\" : {\n" +
                                       "    \"parentFolderId\" : \"f2d804b6-6470-47f8-91e6-78dc1aec3422\",\n" +
                                       "    \"totalSubFolderCount\" : 2,\n" +
                                       "    \"subFolder\" : [ {\n" +
                                       "      \"folderMetadata\" : {\n" +
                                       "        \"creationTS\" : \"1597655710476\",\n" +
                                       "        \"folderName\" : \"sada\",\n" +
                                       "        \"folderIdentifier\" : \"1917685a-f29c-4ac5-b48b-c9a23fd66ab6\",\n" +
                                       "        \"folderSize\" : 0,\n" +
                                       "        \"totalFileCount\" : 0\n" +
                                       "      }\n" +
                                       "    }, {\n" +
                                       "      \"folderMetadata\" : {\n" +
                                       "        \"creationTS\" : \"1597655731932\",\n" +
                                       "        \"folderName\" : \"sDs\",\n" +
                                       "        \"folderIdentifier\" : \"87784660-12c9-47b0-b893-f45966f4eb54\",\n" +
                                       "        \"folderSize\" : 0,\n" +
                                       "        \"totalFileCount\" : 0\n" +
                                       "      }\n" +
                                       "    } ],\n" +
                                       "    \"fileList\" : {\n" +
                                       "      \"file\" : [ {\n" +
                                       "        \"mediaMetadata\" : {\n" +
                                       "          \"metaDataVersion\" : \"1.0\",\n" +
                                       "          \"creationTS\" : \"1572529624530\",\n" +
                                       "          \"filename\" : \"Mavenir_PCS_Backup_Sync_Client_Server_API_2.2.docx\",\n" +
                                       "          \"filetype\" : \"application/vnd.openxmlformats-officedocument.wordprocessingml.document\",\n" +
                                       "          \"fileIdentifier\" : \"20191063686ec2-cea2-40ff-a975-1a440b68ab34\",\n" +
                                       "          \"fileSize\" : \"201593\"\n" +
                                       "        },\n" +
                                       "        \"mediaTypeFiles\" : {\n" +
                                       "          \"author\" : \"JCOlney\",\n" +
                                       "          \"company\" : \"Airwide Solutions\",\n" +
                                       "          \"modificationTime\" : \"1572356460000\"\n" +
                                       "        },\n" +
                                       "        \"filePartInfo\" : [ {\n" +
                                       "          \"filePartType\" : \"media\",\n" +
                                       "          \"filePartURL\" : \"/pcs/v1/media/20191063686ec2-cea2-40ff-a975-1a440b68ab34\"\n" +
                                       "        }, {\n" +
                                       "          \"filePartType\" : \"metadata\",\n" +
                                       "          \"filePartURL\" : \"/pcs/v1/metadata/20191063686ec2-cea2-40ff-a975-1a440b68ab34\"\n" +
                                       "        } ]\n" +
                                       "      } ],\n" +
                                       "      \"totalFileCount\" : 1\n" +
                                       "    }\n" +
                                       "  }\n" +
                                       "}\n";

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(5)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void replaceAll(Blackhole bh) {
        String withCorrId = json.replaceAll("\n", "\n" + CORR_ID + " ");
        bh.consume(withCorrId);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void joining(Blackhole bh) {
        String collect = String.join("\n" + CORR_ID + " ", json.split("\n"));
        bh.consume(collect);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void stringBuilder(Blackhole bh) {
        StringBuilder sb = new StringBuilder();

        for (char c : json.toCharArray()) {
            if (c != '\n') {
                sb.append(c);
            } else {
                sb.append("\n" + CORR_ID + " ");
            }
        }

        String s = sb.toString();
        bh.consume(s);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void stringPlus(Blackhole bh) {
        String res = "";
        char[] chars = json.toCharArray();
        for (char aChar : chars) {
            if (aChar != '\n') {
                res += aChar;
            } else {
                res += "\n" + CORR_ID + " ";
            }
        }
        bh.consume(res);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void stringConcat(Blackhole bh) {
        String res = "";
        char[] chars = json.toCharArray();
        for (char aChar : chars) {
            if (aChar != '\n') {
                res = res.concat(String.valueOf(aChar));
            } else {
                res = res.concat("\n" + CORR_ID + " ");
            }
        }
        bh.consume(res);
    }
}
