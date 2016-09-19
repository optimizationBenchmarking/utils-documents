package org.optimizationBenchmarking.utils.document.impl.latex.documentClasses;

import org.optimizationBenchmarking.utils.document.impl.latex.ELaTeXSection;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocument;
import org.optimizationBenchmarking.utils.document.impl.latex.LaTeXDocumentClass;
import org.optimizationBenchmarking.utils.graphics.EPaperSize;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.units.ELength;

/**
 * The {@code elsarticle} document class. In the final format of Elsevier
 * papers, non-free fonts like Gulliver
 * (http://www.gerardunger.com/fontstore/store-gulliver.html) are used.
 * Obviously, I cannot include them into this open source, free software.
 * Thus, we use the standard computer roman fonts here.
 */
public final class ElsevierArticle extends LaTeXDocumentClass {

  /** the {@code elsarticle} document class */
  private static final ElsevierArticle INSTANCE = new ElsevierArticle();

  /** create */
  private ElsevierArticle() {
    super("elsarticle", // class //$NON-NLS-1$
        new String[] { "final" }, // parameters //$NON-NLS-1$
        EPaperSize.A4, // paper size
        "elsarticle-num", // bibtex style //$NON-NLS-1$
        345.0d, // width
        550.0d, // height
        1, // column count
        345.0d, // column width
        ELength.PT, // length unit
        IEEEtranFontPalette.getInstance(), // Elsevier: non-free fonts
        ELaTeXSection.SECTION, // highest supported section type
        ELaTeXSection.PARAGRAPH// lowest supported section type
    );
  }

  /**
   * get the globally shared instance of the {@code elsarticle} document
   * class
   *
   * @return the {@code elsarticle} document class
   */
  public static final ElsevierArticle getInstance() {
    return ElsevierArticle.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final LaTeXDocument doc,
      final ITextOutput setupPackage) {
    super.setup(doc, setupPackage);
    this.requireResources(doc,
        new String[] { "elsarticle.cls", "elsarticle-num.bst" }, //$NON-NLS-1$ //$NON-NLS-2$
        "These files belong to the LaTeX2e package 'elsarticle' for Elsevier articles of Elsevier. $Id: elsarticle.cls,v 1.20 2008-10-13 04:24:12 cvr Exp $ Copyright 2007, 2008, 2009 Elsevier Ltd. This file is part of the 'Elsarticle Bundle'. It may be distributed under the conditions of the LaTeX Project Public License, either version 1.2 of this license or (at your option) any later version. The latest version of this license is in http://www.latex-project.org/lppl.txt and version 1.2 or later is part of all distributions of LaTeX version 1999/12/01 or later."//$NON-NLS-1$
    );
  }
}
