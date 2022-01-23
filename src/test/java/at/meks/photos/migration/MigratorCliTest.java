package at.meks.photos.migration;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@Log
@ExtendWith(MockitoExtension.class)
class MigratorCliTest {

    @InjectMocks
    private MigratorCli migratorCli = new MigratorCli();

    @Mock
    private DirectoryProcessor directoryProcessor;

    @Test
    void given_recursive_true__when_call__then_directory_is_processed_recursive() {
        migratorCli.recursive(true);
        migratorCli.call();
        verify(directoryProcessor).processDirectory(any(), eq(true));
    }

    @Test
    void given_recursive_false__when_call__then_directory_is_NOT_processed_recursive() {
        migratorCli.recursive(false);
        migratorCli.call();
        verify(directoryProcessor).processDirectory(any(), eq(false));
    }

    @SneakyThrows
    @Test
    void given_file_path__when_call__then_file_path_is_processed() {
        File filePath = File.createTempFile("utMigratorClie", "txt");
        migratorCli.path(filePath);

        migratorCli.call();

        verify(directoryProcessor).processDirectory(same(filePath), anyBoolean());
    }
}