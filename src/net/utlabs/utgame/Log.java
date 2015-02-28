package net.utlabs.utgame;

import java.io.*;
import java.util.Date;

/*
* NOTE:
* This class was made before this event
* https://github.com/abernardi597/indie-study-june/blob/master/src/net/june/src/internal/Log.java
*/

/**
 * This class is for logging with timestamps and logging levels. This class writes to
 * an underlying ByteArrayOutputStream that can optionally be flushed to a file
 * when a certain capacity is reached. This is useful for ensuring the Log does not consume
 * too much memory. The Log can also optionally print to the standard mOutput (System.out).
 * In order to have timestamps included at the beginning of the line, use the various log()
 * methods.
 */
public class Log extends PrintStream {

    //Logging levels
    public static final String INFO = "I";
    public static final String DEBUG = "D";
    public static final String WARNING = "W";
    public static final String ERROR = "E";

    /**
     * @return The current time formatted as HH:MM:SS:mmm
     */
    public static String getNow() {
        Date now = new Date(System.currentTimeMillis());
        return String.format("%tT:%tL", now, now);
    }

    /**
     * @return The class in the current stack trace that is not this class, along with the thread ID in hex.
     */
    public static String getThread() {
        Thread t = Thread.currentThread();
        StackTraceElement[] stackTrace = t.getStackTrace();
        int i1 = -1;
        for (int i = 1; i < stackTrace.length; i++)
            if (!stackTrace[i].getClassName().equals(Log.class.getName())) {
                i1 = i;
                break;
            }
        String s = "Unavailable";
        if (i1 != -1) {
            s = stackTrace[i1].getClassName();
            int end = s.lastIndexOf("$");
            if (end < 0)
                end = s.length();
            int beg = s.lastIndexOf(".") + 1;
            s = s.substring(beg, end);
        }
        return String.format("%s(x%3d)", s, t.getId());
    }

    /**
     * A formatted line head using <tt>getNow()</tt> and the provided logging level
     *
     * @param level the logging level to use
     *
     * @return A <tt>String</tt> formatted as "<tt>[HH:MM:SS:mmm] LEVEL: </tt>"
     * @see #getNow()
     */
    public static String lineHead(String level) {
        return String.format("[%s] %s/%s: ", getNow(), level, getThread());
    }

    /**
     * Whether or not to print to the console.
     */
    private boolean mPrint;
    /**
     * The number of bytes written to the underlying <tt>ByteArrayOutputStream</tt>.
     *
     * @see #mOutput
     */
    private long mCount;
    /**
     * The maximum number of bytes to be written to the underlying <tt>ByteArrayOutputStream</tt>.
     *
     * @see #mOutput
     */
    private long mMaxBuffer;
    /**
     * True to write messages to the log file whenever on of the log methods is invoked.
     */
    private boolean mAutoWrite;
    /**
     * A <tt>String</tt> reference to the file to flush the Log to.
     */
    private String mTarget;
    /**
     * The underlying <tt>ByteArrayOutputStream</tt>.
     */
    private ByteArrayOutputStream mOutput;

    /**
     * Constructs a new Log with a buffer size of <tt>max</tt> and a destination file of <tt>target</tt>.
     * The <tt>print</tt> argument determines whether or not logging will output to the standard out. The
     * Log will also print a header with the date of initialization.
     *
     * @param max    buffer size, in bytes
     * @param target path to a file where information is flushed
     * @param auto   true to write to the file when a log method is used
     * @param print  true will print to the console
     *
     * @see #mMaxBuffer
     * @see #mTarget
     * @see #mPrint
     * @see #Log(boolean)
     */
    public Log(long max, String target, boolean auto, boolean print) {
        super(new ByteArrayOutputStream(), true);
        mOutput = (ByteArrayOutputStream) out;
        mMaxBuffer = max;
        this.mTarget = target;
        mAutoWrite = auto;
        mPrint = print;
        if (target != null && !target.isEmpty()) {
            File f = new File(target);
            if (f.exists())
                f.delete();
        }
        println("########################################");
        printf("#### [%Tc] ####", new Date(System.currentTimeMillis()));
        println();
        log("Logging initialized");
    }

    /**
     * Constructs a new Log with an indefinite buffer size, no destination file, and no auto-write
     *
     * @param print true will print to the console
     *
     * @see #mPrint
     * @see #Log(long, String, boolean, boolean)
     */
    public Log(boolean print) {
        this(-1, null, false, print);
    }

    /**
     * Log a <tt>message</tt> at the given <tt>level</tt>.
     *
     * @param message a message to relay in the log
     * @param level   the logging level to use
     *
     * @see #log(String)
     * @see #log(String, Exception)
     * @see #log(Exception)
     */
    public void log(String message, String level) {
        println(lineHead(level) + message);
        if (mAutoWrite)
            writeToFile(true);
    }

    /**
     * Log a <tt>message</tt> at the <tt>INFO<tt/> logging level.
     *
     * @param message a message to relay in the log
     *
     * @see #log(String, String)
     */
    public void log(String message) {
        log(message, INFO);
    }

    /**
     * Log an <tt>Exception</tt> with a <tt>message</tt> at the <tt>ERROR</tt> logging level.
     * This prints the message, then prints the Exceptions stack trace to the log.
     *
     * @param message a message to relay in the log
     * @param e       an <tt>Exception</tt> that has arisen
     *
     * @see #log(String, String)
     * @see Exception#printStackTrace()
     */
    public void log(String message, Exception e) {
        log(message, ERROR);
        e.printStackTrace(this);
        if (mAutoWrite)
            writeToFile(true);
    }

    /**
     * Log an <tt>Exception</tt>. This is equivalent to the call:
     * <br>
     * <tt>log("", e)</tt>
     *
     * @param e an <tt>Exception</tt> that has arisen
     *
     * @see #log(String, Exception)
     */
    public void log(Exception e) {
        log("", e);
    }

    /**
     * Shorthand for <tt>log(message, INFO)</tt>
     */
    public void i(String message) {
        log(message, INFO);
    }

    /**
     * Shorthand for <tt>log(message, DEBUG)</tt>
     */
    public void d(String message) {
        log(message, DEBUG);
    }

    /**
     * Shorthand for <tt>log(message, WARNING)</tt>
     */
    public void w(String message) {
        log(message, WARNING);
    }

    /**
     * Shorthand for <tt>log(message, ERROR)</tt>
     */
    public void e(String message) {
        log(message, ERROR);
    }

    /**
     * Shorthand for <tt>log(message, e)</tt>
     */
    public void e(String message, Exception e) {
        log(message, e);
    }

    /**
     * Shorthand for <tt>log(e)</tt>
     */
    public void e(Exception e) {
        log(e);
    }

    /**
     * In addition to the normal <tt>close()</tt> operation of <tt>PrintStream</tt>, this method also
     * writes the rest of the buffer contents to the <tt>mTarget</tt> file, if it isn't null.
     *
     * @see #mTarget
     * @see java.io.PrintStream#close()
     */
    public void close() {
        super.close();
        if (mTarget != null)
            writeToFile(true);
    }

    @Override
    public void write(int b) {
        if (mPrint)
            System.out.write(b);
        super.write(b);
        mCount++;
        if (mCount >= 0 && mCount >= mMaxBuffer)
            writeToFile(true);
    }

    @Override
    public void write(byte buf[], int off, int len) {
        if (mCount >= 0 && mCount + len > mMaxBuffer) {
            int newLen = (int) (mMaxBuffer - mCount);
            write(buf, off, newLen);
            write(buf, off + newLen, len - newLen);
        }
        else {
            if (mPrint)
                System.out.write(buf, off, len);
            super.write(buf, off, len);
            mCount += len;
            if (mCount >= 0 && mCount == mMaxBuffer)
                writeToFile(true);
        }
    }

    /**
     * Saves the contents of the underlying <tt>ByteArrayOutputStream</tt> to a file, specified by
     * <tt>target</tt>. It is optional to clear the underlying stream via the <tt>reset</tt> argument.
     *
     * @param target path to the destination file
     * @param reset  true to reset the contents of <tt>output</tt>
     */
    public void writeToFile(String target, boolean reset) {
        File dest = new File(target).getAbsoluteFile();
        try (FileOutputStream writer = new FileOutputStream(dest, true)) {
            mOutput.writeTo(writer);
            if (reset) {
                mOutput.reset();
                mCount = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the contents of the underlying <tt>ByteArrayOutputStream</tt> to the file specified as
     * <tt>mTarget</tt>. It is optional to clear the underlying stream via the <tt>reset</tt> argument.
     *
     * @param reset true to reset the contents of <tt>mOutput</tt>
     *
     * @see #mTarget
     * @see #writeToFile(String, boolean)
     */
    public void writeToFile(boolean reset) {
        writeToFile(mTarget, reset);
    }
}
