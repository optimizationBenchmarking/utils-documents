package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;

/** the internal code transformer */
final class _LaTeXCodeCharTransformer extends LaTeXCharTransformer {

  /** the internal, shared instance */
  static final _LaTeXCodeCharTransformer INSTANCE = new _LaTeXCodeCharTransformer();

  /** the code char transformator */
  _LaTeXCodeCharTransformer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String processTransformation(final char input,
      final String output) {
    switch (input) {
      case '#': {
        return "#\\texthashtag#"; //$NON-NLS-1$
      }
      case '"':
      case '$':
      case '%':
      case '&':
      case '<':
      case '>':
      case '*':
      case '\\':
      case '^':
      case '_':
      case '\'':
      case '{':
      case '|':
      case '}':
      case '~': {
        return String.valueOf(input);
      }
      default: {
        return ((("#\\small\\ttfamily{" + output) + '}') + '#');//$NON-NLS-1$
      }
    }
  }
}
