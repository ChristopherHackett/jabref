package net.sf.jabref.logic.exporter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.sf.jabref.BibDatabaseContext;
import net.sf.jabref.logic.importer.fileformat.BibtexImporter;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.preferences.JabRefPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MSBibExportFormatTestFiles {

    public BibDatabaseContext databaseContext;
    public Charset charset;
    public File tempFile;
    public MSBibExportFormat msBibExportFormat;
    public BibtexImporter testImporter;

    @Parameter
    public String filename;
    public Path resourceDir;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Parameters(name = "{0}")
    public static Collection<String> fileNames() throws IOException, URISyntaxException {
        try (Stream<Path> stream = Files.list(Paths.get(MSBibExportFormatTestFiles.class.getResource("").toURI()))) {
            return stream.map(n -> n.getFileName().toString()).filter(n -> n.endsWith(".bib"))
                    .filter(n -> n.startsWith("MsBib")).collect(Collectors.toList());
        }
    }

    @Before
    public void setUp() throws Exception {
        resourceDir = Paths.get(MSBibExportFormatTestFiles.class.getResource("").toURI());
        databaseContext = new BibDatabaseContext();
        charset = StandardCharsets.UTF_8;
        msBibExportFormat = new MSBibExportFormat();
        tempFile = testFolder.newFile();
        testImporter = new BibtexImporter(JabRefPreferences.getInstance().getImportFormatPreferences());
    }

    @Test
    public final void testPerformExport() throws IOException, SaveException {
        String xmlFileName = filename.replace(".bib", ".xml");
        Path importFile = resourceDir.resolve(filename);
        String tempFilename = tempFile.getCanonicalPath();
        List<BibEntry> entries = testImporter.importDatabase(importFile, StandardCharsets.UTF_8).getDatabase()
                .getEntries();

        msBibExportFormat.performExport(databaseContext, tempFile.getPath(), charset, entries);

        List<String> expected = Files.readAllLines(resourceDir.resolve(xmlFileName));
        List<String> exported = Files.readAllLines(Paths.get(tempFilename));
        Collections.sort(expected);
        Collections.sort(exported);
        assertEquals(expected, exported);
    }
}
