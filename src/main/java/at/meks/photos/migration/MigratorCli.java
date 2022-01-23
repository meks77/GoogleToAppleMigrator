package at.meks.photos.migration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Accessors(fluent = true, chain = true)
@Log
public class MigratorCli implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "the directory of the google takeout directory, which contains the photos")
    private File path;

    @CommandLine.Option(names = {"-r", "--recursive"}, description = "the directory and all of its subdirectories should be processed")
    private boolean recursive;

    private DirectoryProcessor directoryProcessor = new DirectoryProcessor();

    @Override
    public Integer call() {
        log.info("migration started");
        directoryProcessor.processDirectory(path, recursive);
        return 0;
    }

    public static void main(String[] args) {
        final int exitCode = new CommandLine(new MigratorCli()).execute(args);
        System.exit(exitCode);
    }
}
