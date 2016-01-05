package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.impl.Styles;
import org.optimizationBenchmarking.utils.graphics.style.spec.IStylesProvider;

/**
 * A document part which can provide a style.
 */
class _StyleProviderPart extends DocumentPart implements IStylesProvider {

  /** the styles */
  final Styles m_styles;

  /**
   * Create a document part.
   *
   * @param owner
   *          the owning FSM
   */
  _StyleProviderPart(final DocumentElement owner) {
    super(owner);
    this.m_styles = _StyleProviderPart._createStyles(owner);
  }

  /**
   * Create the style set
   *
   * @param owner
   *          the owner
   * @return the style set
   */
  @SuppressWarnings("resource")
  static final Styles _createStyles(final DocumentElement owner) {
    Styles ss;
    DocumentElement x;

    finder: {
      for (x = owner; x != null; x = x._owner()) {
        if (x instanceof _StyleProviderPart) {
          ss = ((_StyleProviderPart) x).m_styles;
          break finder;
        }
        if (x instanceof Document) {
          ss = ((Document) x).m_styles;
          break finder;
        }
        if (x instanceof IStylesProvider) {
          ss = ((Styles) (((IStylesProvider) x).getStyles()));
          break finder;
        }
      }
      throw new IllegalArgumentException("Could not find style set."); //$NON-NLS-1$
    }

    return new Styles(ss);
  }

  /** {@inheritDoc} */
  @Override
  public final Styles getStyles() {
    return this.m_styles;
  }
}
