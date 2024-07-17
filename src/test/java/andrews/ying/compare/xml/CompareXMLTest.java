// Examples from: https://howtodoinjava.com/java/library/xmlassert-assertions/
// WARNING: the import should be org.xmlunit.assertj3.XmlAssert.assertThat, not org.xmlunit.assertj.XmlAssert.assertThat

package andrews.ying.compare.xml;

import org.junit.Test;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;
import static org.xmlunit.assertj3.XmlAssert.assertThat;

public class CompareXMLTest {

    @Test
    public void assertJcompare() {
        String xml = "<widget><id>1</id><name>demo</name></widget>";
        String identicalXml = "<widget><id>1</id><name>demo</name></widget>";
        String similarXml = "<widget><name>demo</name><id>1</id></widget>";
        assertThat(xml).and(identicalXml).areIdentical();
        assertThat(xml).and(similarXml).areNotSimilar();
        assertThat(xml).and(similarXml).areNotIdentical();

        assertThat(xml).and(similarXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .areSimilar();

        assertThat(xml).and(similarXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .areNotIdentical();

        assertThat(xml).and("<widget><id>1</id></widget>")
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .areNotSimilar();
    }
}
