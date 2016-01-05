package org.optimizationBenchmarking.utils.tools.impl.latex;

import org.optimizationBenchmarking.utils.collections.iterators.IterableIterator;

/**
 * This {@link java.lang.Iterable} provides a list of all main LaTeX
 * engines. The LaTeX tool will pick the right engine for the right file
 * types. The order of engines in here represents their priority.
 * Currently, we priorize LuaTeX, as it can seemingly dynamically change
 * (grow) the internal data structures. This comes in handy when compiling
 * large documents or documents with
 * {@link org.optimizationBenchmarking.utils.graphics.graphic.impl.EGraphicFormat#PGF
 * PGF} figures.
 */
final class _AllEngines
    extends IterableIterator<_LaTeXToolChainComponentDesc> {

  /** the index */
  private int m_index;

  /** create */
  _AllEngines() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_index <= 11);
  }

  /** {@inheritDoc} */
  @Override
  public final _LaTeXToolChainComponentDesc next() {
    switch (this.m_index++) {
      case 0: {
        return _LuaLaTeX._getDescription(false);
      }
      case 1: {
        return _LuaTeXAsLuaLaTeX._getDescription(false);
      }
      case 2: {
        return _PdfLaTeX._getDescription();
      }
      case 3: {
        return _LaTeX._getDescription();
      }
      case 4: {
        return _PdfTeXAsPdfLaTeX._getDescription();
      }
      case 5: {
        return _PdfTeXAsLaTeX._getDescription();
      }
      case 6: {
        return _LaTeXAsPdfLaTeX._getDescription();
      }
      case 7: {
        return _PdfLaTeXAsLaTeX._getDescription();
      }
      case 8: {
        return _XeLaTeX._getDescription();
      }
      case 9: {
        return _XeTeXAsXeLaTeX._getDescription();
      }
      case 10: {
        return _LuaLaTeX._getDescription(true);
      }
      case 11: {
        return _LuaTeXAsLuaLaTeX._getDescription(true);
      }
      default: {
        return super.next(); // no more elements
      }
    }
  }
}
