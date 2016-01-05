package examples.org.optimizationBenchmarking.utils.document;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticleBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.document.template.DocumentXMLHandler;
import org.optimizationBenchmarking.utils.document.template.DocumentXMLInput;
import org.optimizationBenchmarking.utils.document.template.IDocumentCallback;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.numbers.SimpleNumberAppender;

/**
 * A template-based document creation example.
 */
public class TemplateDocumentExample extends DocumentExample {

  /** the singleton bib record */
  public static final BibRecord STATIC_BIB_ENTRY;

  static {
    try (final BibliographyBuilder local = new BibliographyBuilder()) {
      try (final BibArticleBuilder bab = local.article()) {
        try (final BibDateBuilder d = bab.date()) {
          d.fromNow();
        }
        bab.setTitle("A very important article");//$NON-NLS-1$
        bab.setJournal("The journal of important things");//$NON-NLS-1$
        bab.setStartPage("1");//$NON-NLS-1$
        bab.setEndPage("10");//$NON-NLS-1$
        bab.setVolume("4");//$NON-NLS-1$
        bab.setNumber("33");//$NON-NLS-1$
        try (final BibAuthorsBuilder aa = bab.setAuthors()) {
          try (final BibAuthorBuilder a = aa.author()) {
            a.setFamilyName("Funnyman");//$NON-NLS-1$
            a.setPersonalName("Jake");//$NON-NLS-1$
          }
        }
      }

      STATIC_BIB_ENTRY = local.getResult().get(0);
    }
  }

  /**
   * create
   *
   * @param doc
   *          the document
   */
  public TemplateDocumentExample(final IDocument doc) {
    super(doc);
  }

  /** {@inheritDoc} */
  @Override
  public final void run() {
    final HashMap<Object, Object> properties;

    properties = new HashMap<>();
    properties.put("numberAppender", SimpleNumberAppender.INSTANCE);//$NON-NLS-1$
    properties.put("numberFormat", new DecimalFormat("0.00"));//$NON-NLS-1$//$NON-NLS-2$
    properties.put("myNumber", Float.valueOf(0.1234f));//$NON-NLS-1$
    properties.put("printMap", new __PrintMapCallback());//$NON-NLS-1$

    try (final IDocumentHeader head = this.m_doc.header()) {
      RandomDocumentExample._createRandomHeader(head, //
          this.m_doc.getClass().getSimpleName(), new Random());
    }

    try (final IDocumentBody body = this.m_doc.body()) {

      try {
        DocumentXMLInput.getInstance().use()
            .setDestination(new DocumentXMLHandler(body, properties))
            .addResource(TemplateDocumentExample.class,
                "exampleTemplate.template") //$NON-NLS-1$
            .create().call();
      } catch (final IOException ioe) {
        RethrowMode.AS_RUNTIME_EXCEPTION.rethrow(//
            "Error while executing document template.", //$NON-NLS-1$
            true, ioe);
      }
    }

    try (final IDocumentBody footer = this.m_doc.footer()) {
      //
    }

    this.m_doc.close();
  }

  /** an internal callback */
  private static final class __PrintMapCallback
      implements IDocumentCallback<IText> {
    /** create */
    __PrintMapCallback() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final void callback(final IText element,
        final Map<Object, Object> properties) {
      element.append(//
          "This is a callback invocation printing the properties map: "); //$NON-NLS-1$
      element.append(properties);
    }

    /** {@inheritDoc} */
    @Override
    public final Class<IText> getElementClass() {
      return IText.class;
    }
  }
}
