package org.jboss.training.wls.impl;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
abstract class ArquillianTest {
    protected static final String WEBAPP_SRC = "src/main/webapp";

    protected static final String MANAGED_CONTAINER = "managed-container";
    protected static final String DEPLOYMENT_MANAGED = "test-app";

    /**
     * Deployment for managed container.
     * @return WAR
     */
    @TargetsContainer(ArquillianTest.MANAGED_CONTAINER)
    @Deployment(name = ArquillianTest.DEPLOYMENT_MANAGED)
    public static WebArchive managedModeDeployment() {
        return ArquillianTest.createDeployment();
    }

    protected static WebArchive createDeployment() {
        final Set<String> dependencies = new HashSet<String>();
        dependencies.add("org.assertj:assertj-core");

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(ArquillianTest.resolveDependencies(dependencies))
                .addPackages(true, "org.jboss.training.wls")
                .setWebXML(new File(ArquillianTest.WEBAPP_SRC, "WEB-INF/web.xml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("META-INF/beans.xml"))
                .addAsWebInfResource(new File(ArquillianTest.WEBAPP_SRC, "WEB-INF/ejb-jar.xml"));
    }

    protected static File[] resolveDependencies(final Collection<String> dependencies) {
        final String[] artifacts = dependencies.toArray(new String[dependencies.size()]);

        final MavenResolverSystem resolver = Maven.resolver();
        return resolver.loadPomFromFile("pom.xml")
                .resolve(artifacts).withTransitivity().asFile();
    }
}
