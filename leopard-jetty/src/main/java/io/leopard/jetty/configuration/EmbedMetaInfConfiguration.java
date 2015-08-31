package io.leopard.jetty.configuration;

import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbedMetaInfConfiguration extends MetaInfConfiguration {

	// protected void addTldResource(final WebAppContext context, Resource resource, String name) throws IOException {
	// Resource tldResource = resource.getResource(name);
	// if (tldResource.exists()) {
	// addResource(context, METAINF_TLDS, tldResource);
	// }
	// }
	//
	// protected void addFolderResource(final WebAppContext context) throws IOException {
	// for (Resource resource : context.getMetaData().getWebInfJars()) {
	// String url = resource.toString();
	// if (!isClassesDir(url)) {
	// continue;
	// }
	// {
	// Resource fragmentResource = resource.getResource("META-INF/web-fragment.xml");
	// if (fragmentResource.exists()) {
	// addResource(context, METAINF_FRAGMENTS, resource);
	// }
	// }
	//
	// this.addTldResource(context, resource, "META-INF/fnx.tld");
	// this.addTldResource(context, resource, "META-INF/dw.tld");
	// }
	// }

	@Override
	public void scanForFragment(WebAppContext context, Resource jar, ConcurrentHashMap<Resource, Resource> cache) throws Exception {
		// System.out.println("jar111:" + jar.toString());
		super.scanForFragment(context, jar, cache);
	}

	protected boolean isClassesDir(String url) {
		return url.endsWith("/classes/") || url.endsWith("/classes");
	}

	// @Override
	// public void preConfigure(final WebAppContext context) throws Exception {
	// // this.addFolderResource(context);
	// // System.out.println("preConfigure:" + context);
	// super.preConfigure(context);
	//
	// @SuppressWarnings("unchecked")
	// Map<Resource, Resource> frags = (Map<Resource, Resource>) context.getAttribute(METAINF_FRAGMENTS);
	// if (frags != null) {
	// for (Resource key : frags.keySet()) {
	// System.err.println("key1:" + key);
	// }
	// }
	// }

}
