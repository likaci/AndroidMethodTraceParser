package com.xiazhiri.android.traceparser;

import com.android.tools.profilers.cpu.CaptureNode;
import com.android.tools.profilers.cpu.CpuCapture;
import com.android.tools.profilers.cpu.TraceParser;
import com.android.tools.profilers.cpu.art.ArtTraceParser;

import java.io.File;
import java.io.IOException;

public class Main {

    private static TraceParser parser;
    private static long totalDuration;

    public static void main(String[] args) throws IOException {
        File trace = new File("cpu_trace.trace");
        parser = new ArtTraceParser();
        parser.parse(trace);
        CpuCapture cpuCapture = new CpuCapture(parser.getRange(), parser.getCaptureTrees());
        CaptureNode node = cpuCapture.getCaptureNode(3001);
        totalDuration = node.duration();
        printNode(node, 10, node.duration() / 70);
    }

    private static void printNode(CaptureNode node, int maxDepth, long minDuration) {
        if (node.getDepth() <= maxDepth && node.duration() >= minDuration) {
            for (int i = 0; i < node.getDepth(); i++) {
                System.out.print("-");
            }
            System.out.printf(" %d %.2f %s %s", node.duration(), node.duration() * 100F / totalDuration, node.getMethodModel().getClassName(), node.getMethodModel().getName());
            System.out.print("\n");
            for (CaptureNode captureNode : node.getChildren()) {
                printNode(captureNode, maxDepth, minDuration);
            }
        }
    }

}
