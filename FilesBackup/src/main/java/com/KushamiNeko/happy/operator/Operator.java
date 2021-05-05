package com.KushamiNeko.happy.operator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.KushamiNeko.happy.pretty.Pretty;
import com.google.common.io.Files;

public abstract class Operator {

  final String srcDir;
  final String dstDir;

  int downloadCount = 0;
  int renameCount = 0;
  int checkMissingCount = 0;

  int processCount = 0;
  final int processLimit = 10;

  Operator() {
    final String home = System.getenv("HOME");
    this.srcDir = Paths.get(home, "Downloads").toString();
    this.dstDir = Paths.get(home, "Documents", "data_source").toString();
  }

  Operator(String srcDir, String dstDir) {
    if (!new File(srcDir).exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", srcDir));
    }

    if (!new File(dstDir).exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", dstDir));
    }

    this.srcDir = srcDir;
    this.dstDir = dstDir;
  }

  public abstract void download();

  public abstract void rename();

  public abstract void check();

  private void downloadCommand(String page) {
    try {
      Runtime.getRuntime().exec(String.format("firefox %s", page));
    } catch (IOException err) {
      this.error(err);
    }
  }

  private void renameCommand(String src, String dst) {
    final File srcFile = new File(src);
    final File dstFile = new File(dst);

    try {
      com.google.common.io.Files.move(srcFile, dstFile);
    } catch (IOException err) {
      this.error(err);
    }
  }

  void downloadOp(String page, String message) {

    this.downloadMessage(message);

    this.downloadCommand(page);

    this.downloadCount += 1;
  }

  void renameOp(String src, String dst) {
    File f;

    f = new File(this.srcDir);
    if (!f.exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", this.srcDir));
    }

    f = new File(this.dstDir);
    if (!f.exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", this.dstDir));
    }

    f = new File(src);
    if (!f.exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", src));
    }

    f = new File(dst);
    if (!f.getParentFile().exists()) {
      throw new IllegalArgumentException(String.format("path does not exist: %s", dst));
    }

    this.renameMessage(src, dst);

    this.renameCommand(src, dst);

    this.renameCount += 1;
  }

  void checkOp(String filePath) {
    File f = new File(filePath);

    if (!f.exists()) {
      this.checkMessage(f.getName().replaceAll(String.format(".%s", Files.getFileExtension(f.getName())), ""));
      this.checkMissingCount += 1;
    }
  }

  void downloadMessage(String symbol) {
    Pretty.colorPrintln(Pretty.PaperCyan300, String.format("downloading: %s", symbol));
  }

  void renameMessage(String src, String dst) {
    Pretty.colorPrintln(Pretty.PaperOrange300, String.format("%s -> %s", src, dst));
  }

  void checkMessage(String symbol) {
    Pretty.colorPrintln(Pretty.PaperIndigo300, String.format("missing: %s", symbol));
  }

  void downloadCompleted() {
    Pretty.colorPrintln(Pretty.PaperLightGreenA200, String.format("%d files downloaded", this.downloadCount));

    Pretty.colorPrintln(Pretty.PaperGreen400, "download completed");
    this.completed();
  }

  void renameCompleted() {
    Pretty.colorPrintln(Pretty.PaperLightGreenA200, String.format("rename %d files", this.renameCount));

    if (this.downloadCount != this.renameCount) {
      Pretty.colorPrintln(Pretty.PaperRed600, "rename operation miss some downloaded files");
    }

    Pretty.colorPrintln(Pretty.PaperGreen400, "rename completed");
    this.completed();
  }

  void checkCompleted() {
    Pretty.colorPrintln(Pretty.PaperLightGreenA200, String.format("check missing %d files", this.checkMissingCount));

    Pretty.colorPrintln(Pretty.PaperGreen400, "check completed");
    this.completed();
  }

  void checkProcessLimit() {
    this.processCount += 1;
    if (this.processCount >= this.processLimit) {
      this.completed();
      this.processCount = 0;
    }
  }

  void completed() {
    Pretty.colorPrintln(Pretty.PaperBrown300, "press any key to continue");
    System.console().readLine();
  }

  void error(Exception err) {
    Pretty.colorPrintln(Pretty.PaperRed400, String.format("error: %s", err.getMessage()));
  }

}
