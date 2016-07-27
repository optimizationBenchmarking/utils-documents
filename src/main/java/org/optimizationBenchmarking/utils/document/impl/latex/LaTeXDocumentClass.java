package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.comparison.Compare;
import org.optimizationBenchmarking.utils.document.impl.latex.documentClasses.Article;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.graphics.PageDimension;
import org.optimizationBenchmarking.utils.graphics.style.spec.IFontPalette;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.units.ELength;

/** A document class for LaTeX documents */
public class LaTeXDocumentClass extends PageDimension {

  /** the class to use */
  private final String m_class;

  /** the class parameters */
  private final ArrayListView<String> m_classParams;

  /** the bibliography style to use */
  private final String m_bibStyle;

  /** the page size */
  private final EPaperSize m_paperSize;

  /** the font palette */
  private final IFontPalette m_fonts;

  /**
   * the highest supported section type, e.g., {@link ELaTeXSection#PART}
   */
  private final ELaTeXSection m_highest;
  /**
   * the lowest supported section type, e.g.,
   * {@link ELaTeXSection#SUBSECTION}
   */
  private final ELaTeXSection m_lowest;

  /**
   * create a new document class
   *
   * @param clazz
   *          the class
   * @param params
   *          the parameters of this class
   * @param paperSize
   *          the paper size
   * @param bibStyle
   *          the bibliography style to use
   * @param width
   *          the width
   * @param height
   *          the height
   * @param columnCount
   *          the number of columns
   * @param columnWidth
   *          the width of a column
   * @param unit
   *          the length unit
   * @param fonts
   *          the font palette of this document class
   * @param highestSection
   *          the highest supported section type, e.g.,
   *          {@link ELaTeXSection#PART}
   * @param lowestSection
   *          the lowest supported section type, e.g.,
   *          {@link ELaTeXSection#SUBSECTION}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public LaTeXDocumentClass(final String clazz, final String[] params,
      final EPaperSize paperSize, final String bibStyle,
      final double width, final double height, final int columnCount,
      final double columnWidth, final ELength unit,
      final IFontPalette fonts, final ELaTeXSection highestSection,
      final ELaTeXSection lowestSection) {
    super(width, height, columnCount, columnWidth, unit);

    if ((this.m_class = TextUtils.normalize(clazz)) == null) {
      throw new IllegalArgumentException(//
          "Document class name cannot be empty or null, but is '" //$NON-NLS-1$
              + clazz + '\'');
    }
    if ((this.m_bibStyle = TextUtils.normalize(bibStyle)) == null) {
      throw new IllegalArgumentException(//
          "Bibliography style cannot be empty or null, but is '" //$NON-NLS-1$
              + bibStyle + '\'');
    }
    if (paperSize == null) {
      throw new IllegalArgumentException(//
          "Paper size cannot be null."); //$NON-NLS-1$
    }
    this.m_paperSize = paperSize;
    if ((params == null) || (params.length <= 0)) {
      this.m_classParams = ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    } else {
      this.m_classParams = new ArrayListView(params.clone(), false);
    }

    if (fonts == null) {
      this.m_fonts = LaTeXDefaultFontPalette.getInstance();
    } else {
      this.m_fonts = fonts;
    }

    this.m_highest = ((highestSection != null) ? highestSection
        : ELaTeXSection.SECTION);
    this.m_lowest = ((lowestSection != null) ? lowestSection
        : ELaTeXSection.SUBSUBSECTION);
    if (this.m_highest.ordinal() > this.m_lowest.ordinal()) {
      throw new IllegalArgumentException(//
          "If the highest supported document section type is " //$NON-NLS-1$
              + this.m_highest
              + ", then the lowest supported document section type cannot be " //$NON-NLS-1$
              + this.m_lowest + ", since this is 'semantically bigger'."); //$NON-NLS-1$
    }
  }

  /**
   * Get the document class name
   *
   * @return the document class name
   */
  public final String getDocumentClass() {
    return this.m_class;
  }

  /**
   * Get the document class parameters
   *
   * @return the document class parameters
   */
  public final ArrayListView<String> getDocumentClassParameters() {
    return this.m_classParams;
  }

  /**
   * Get the bibliography style name
   *
   * @return the bibliography style name
   */
  public final String getBibliographyStyle() {
    return this.m_bibStyle;
  }

  /**
   * Get the paper size
   *
   * @return the paper size
   */
  public final EPaperSize getPaperSize() {
    return this.m_paperSize;
  }

  /**
   * Get the font palette
   *
   * @return the font palette
   */
  public final IFontPalette getFontPalette() {
    return this.m_fonts;
  }

  /**
   * Get the highest supported section type, e.g.,
   * {@link ELaTeXSection#PART}
   *
   * @return the highest supported section type
   */
  public final ELaTeXSection getHighestSectionType() {
    return this.m_highest;
  }

  /**
   * Get the lowest supported section type, e.g.,
   * {@link ELaTeXSection#SUBSECTION}
   *
   * @return the lowest supported section type
   */
  public final ELaTeXSection getLowestSectionType() {
    return this.m_lowest;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_class);
    if (!(this.m_classParams.isEmpty())) {
      this.m_classParams.toText(textOut);
    }
    textOut.append('{');
    super.toText(textOut);
    textOut.append(", with bibliography style "); //$NON-NLS-1$
    textOut.append(this.m_bibStyle);
    textOut.append('}');
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(
        //
        HashUtils.combineHashes(super.hashCode(), //
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_class), //
                HashUtils.hashCode(this.m_classParams))), //
        HashUtils.combineHashes(
            //
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_bibStyle), //
                HashUtils.hashCode(this.m_fonts)), //
            HashUtils.combineHashes(
                //
                HashUtils.hashCode(this.m_highest),
                HashUtils.hashCode(this.m_lowest))));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object o) {
    final LaTeXDocumentClass c;

    if (o == this) {
      return true;
    }

    if (o instanceof LaTeXDocumentClass) {
      c = ((LaTeXDocumentClass) o);

      return (Compare.equals(this.m_class, c.m_class) && //
          Compare.equals(this.m_classParams, c.m_classParams) && //
          Compare.equals(this.m_bibStyle, c.m_bibStyle) && //
          Compare.equals(this.m_fonts, c.m_fonts) && //
          Compare.equals(this.m_highest, c.m_highest) && //
          Compare.equals(this.m_lowest, c.m_lowest) && //
          super.equals(o));
    }
    return false;
  }

  /**
   * Get the command that begins the summary
   *
   * @return the command that begins the summary
   */
  protected String getSummaryBegin() {
    return "\\begin{abstract}"; //$NON-NLS-1$
  }

  /**
   * Get the command that ends the summary
   *
   * @return the command that ends the summary
   */
  protected String getSummaryEnd() {
    return "\\end{abstract}";//$NON-NLS-1$
  }

  /**
   * Get the command that begins the title
   *
   * @return the command that begins the title
   */
  protected String getTitleBegin() {
    return "\\title{"; //$NON-NLS-1$
  }

  /**
   * Get the command that ends the title
   *
   * @return the command that ends the title
   */
  protected String getTitleEnd() {
    return "}";//$NON-NLS-1$
  }

  /**
   * Get the command that begins the authors
   *
   * @return the command that begins the authors
   */
  protected String getAuthorsBegin() {
    return "\\author{"; //$NON-NLS-1$
  }

  /**
   * Get the command that ends the authors
   *
   * @return the command that ends the authors
   */
  protected String getAuthorsEnd() {
    return "}";//$NON-NLS-1$
  }

  /**
   * Get the command that begins an author
   *
   * @return the command that begins an author
   */
  protected String getAuthorBegin() {
    return null;
  }

  /**
   * Get the command that ends an author
   *
   * @return the command that ends an author
   */
  protected String getAuthorEnd() {
    return null;
  }

  /**
   * Get the command that separates two authors
   *
   * @return the command that separates two authors
   */
  protected String getAuthorSeparator() {
    return " \\and "; //$NON-NLS-1$
  }

  /**
   * Get the command that begins the date
   *
   * @return the command that begins the date
   */
  protected String getDateBegin() {
    return "\\date{"; //$NON-NLS-1$
  }

  /**
   * Get the command that ends the date
   *
   * @return the command that ends the date
   */
  protected String getDateEnd() {
    return "}";//$NON-NLS-1$
  }

  /**
   * Get the command that makes the title
   *
   * @return the command that makes the title
   */
  protected String getMakeTitle() {
    return "\\maketitle";//$NON-NLS-1$
  }

  /**
   * Initialize a LaTeX document. This mainly means to copy resources to it
   * via {@link #requireResources(LaTeXDocument, String[], String)} and to
   * load packages via
   * {@link #requirePackage(ITextOutput, String, String[])}.
   *
   * @param doc
   *          the document to initialize
   * @param setupPackage
   *          the text output device of the setup package
   * @see #requireResources(LaTeXDocument, String[], String)
   * @see #requirePackage(ITextOutput, String, String[])
   */
  protected void setup(final LaTeXDocument doc,
      final ITextOutput setupPackage) {
    //
  }

  /**
   * Require that a set of resources (located together with this class)
   * should be copied to the location of the LaTeX document {@code doc}.
   *
   * @param doc
   *          the document to which the resources should be co-located
   * @param resources
   *          the list of resources
   * @param license
   *          the license under which the resources are distributed
   * @see #setup(LaTeXDocument, ITextOutput)
   */
  protected final void requireResources(final LaTeXDocument doc,
      final String[] resources, final String license) {
    doc._requireResources(this.getClass(), resources, license);
  }

  /**
   * Require a package.
   *
   * @param setupPackage
   *          the text output to write to
   * @param packageName
   *          the package name
   * @param options
   *          the package options
   * @see #setup(LaTeXDocument, ITextOutput)
   */
  protected final void requirePackage(final ITextOutput setupPackage,
      final String packageName, final String[] options) {
    LaTeXDocument._requirePackage(setupPackage, packageName, options);
  }

  /**
   * Write a comment and end the line.
   *
   * @param comment
   *          the comment
   * @param out
   *          the text output
   */
  protected static final void commentLine(final String comment,
      final ITextOutput out) {
    LaTeXDriver._commentLine(comment, out);
  }

  /**
   * End the line
   *
   * @param out
   *          the text output
   */
  protected static final void endLine(final ITextOutput out) {
    LaTeXDriver._endLine(out);
  }

  /**
   * Get the default document class
   *
   * @return the default document class
   */
  public static final LaTeXDocumentClass getDefaultDocumentClass() {
    return Article.getInstance();
  }
}
