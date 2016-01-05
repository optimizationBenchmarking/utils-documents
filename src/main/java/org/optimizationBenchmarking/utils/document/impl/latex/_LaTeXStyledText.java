package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.style.impl.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.impl.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStyle;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a styled text in a LaTeX document */
final class _LaTeXStyledText extends StyledText {
  /** the begin normal text */
  static final char[] TEXT_NORMAL_BEGIN = { '{', '\\', 't', 'e', 'x', 't',
      'n', 'o', 'r', 'm', 'a', 'l', '{', };
  /** the begin emphasize */
  static final char[] EMPH_BEGIN = { '{', '\\', 'e', 'm', 'p', 'h', '{', };
  /** the begin code */
  private static final char[] CODE_BEGIN = { '{', '\\', 't', 'e', 'x', 't',
      't', 't', '{', };
  /** begin black stuff */
  private static final char[] BLACK_BEGIN = { '{', '\\', 'c', 'o', 'l',
      'o', 'r', '{', 'b', 'l', 'a', 'c', 'k', '}', '{', };
  /** begin colored stuff */
  private static final char[] COLOR_BEGIN = { '{', '\\', 'c', 'o', 'l',
      'o', 'r', '{', };
  /** the begin italic */
  private static final char[] TEXTIT_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 'i', 't', '{', };
  /** the begin bold */
  private static final char[] TEXTBF_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 'b', 'f', '{', };
  /** the begin sans-serif */
  private static final char[] TEXTSF_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 's', 'f', '{', };
  /** the begin roman */
  private static final char[] TEXTRM_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 'r', 'm', '{', };
  /** the begin textit */
  private static final char[] TEXTUL_BEGIN = { '{', '\\', 'u', //
      // 'n', 'd', 'e', 'r',
      'l', 'i', 'n', 'e', '{', };
  /** the begin text type writer */
  private static final char[] TEXTTT_BEGIN = _LaTeXStyledText.CODE_BEGIN;

  /**
   * create the styled text
   *
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _LaTeXStyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    final ITextOutput textOut;

    this.assertNoChildren();
    textOut = this.getTextOutput();
    textOut.appendLineBreak();
    textOut.appendLineBreak();
  }

  /** {@inheritDoc} */
  @Override
  protected final void beginColor(final ColorStyle color,
      final ITextOutput out) {
    out.append(_LaTeXStyledText.COLOR_BEGIN);
    out.append(((LaTeXDocument) (this.getDocument()))._colorName(color));
    out.append('}');
    out.append('{');
  }

  /** {@inheritDoc} */
  @Override
  protected final void endColor(final ColorStyle color,
      final ITextOutput out) {
    out.append('}');
    out.append('}');
  }

  /** {@inheritDoc} */
  @Override
  protected final void beginBlack(final ColorStyle color,
      final ITextOutput out) {
    out.append(_LaTeXStyledText.BLACK_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void endBlack(final ColorStyle color,
      final ITextOutput out) {
    out.append('}');
    out.append('}');
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final void beginFont(final FontStyle font,
      final ITextOutput out) {
    final EFontFamily got;

    if (font.isItalic()) {
      out.append(_LaTeXStyledText.TEXTIT_BEGIN);
    }
    if (font.isBold()) {
      out.append(_LaTeXStyledText.TEXTBF_BEGIN);
    }

    got = font.getFamily();
    if (got != this.getDocument().getStyles().getDefaultFont()
        .getFamily()) {
      switch (got) {
        case SERIF: {
          out.append(_LaTeXStyledText.TEXTRM_BEGIN);
          break;
        }
        case SANS_SERIF: {
          out.append(_LaTeXStyledText.TEXTSF_BEGIN);
          break;
        }
        case MONOSPACED: {
          out.append(_LaTeXStyledText.TEXTTT_BEGIN);
          break;
        }
      }
    }

    if (font.isUnderlined()) {
      ((LaTeXDocument) (this.getDocument()))._registerUnderlined();
      out.append(_LaTeXStyledText.TEXTUL_BEGIN);
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("incomplete-switch")
  @Override
  protected final void endFont(final FontStyle font,
      final ITextOutput out) {
    final EFontFamily got;

    if (font.isItalic()) {
      out.append('}');
      out.append('}');
    }
    if (font.isBold()) {
      out.append('}');
      out.append('}');
    }

    got = font.getFamily();
    if (got != this.getDocument().getStyles().getDefaultFont()
        .getFamily()) {
      switch (got) {
        case SERIF:
        case SANS_SERIF:
        case MONOSPACED: {
          out.append('}');
          out.append('}');
          break;
        }
      }
    }

    if (font.isUnderlined()) {
      out.append('}');
      out.append('}');
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void beginCode(final FontStyle font,
      final ITextOutput out) {
    out.append(_LaTeXStyledText.CODE_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void endCode(final FontStyle font,
      final ITextOutput out) {
    out.append('}');
    out.append('}');
  }

  /** {@inheritDoc} */
  @Override
  protected final void beginEmph(final FontStyle font,
      final ITextOutput out) {
    out.append(_LaTeXStyledText.EMPH_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void endEmph(final FontStyle font,
      final ITextOutput out) {
    out.append('}');
    out.append('}');
  }

  /** {@inheritDoc} */
  @Override
  protected final void beginNormalText(final FontStyle font,
      final ITextOutput out) {
    out.append(_LaTeXStyledText.TEXT_NORMAL_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void endNormalText(final FontStyle font,
      final ITextOutput out) {
    out.append('}');
    out.append('}');
  }

}
