package io.leopard.jetty.configuration;

import org.eclipse.jetty.webapp.MetaInfConfiguration;

public class EmbedMetaInfConfiguration extends MetaInfConfiguration {

	// protected void addTldResource(final WebAppContext context, Resource resource, String name) throws IOException {
	// Resource tldResource = resource.getResource(name);
	// if (tldResource.exists()) {
	// // System.err.println("tldResource:" + tldResource);
	// // addResource(context, METAINF_TLDS, Resource.newResource(tldResource.getURL()));
	// addResource(context, METAINF_TLDS, tldResource);
	// }
	// }
	//
	// protected void addFolderResource(final WebAppContext context) throws IOException {
	// for (Resource resource : context.getMetaData().getWebInfJars()) {
	// String url = resource.toString();
	// // if (!url.endsWith(".jar")) {
	// // System.err.println("url:" + url);
	// // }
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
	//
	// this.addTldResource(context, resource, "META-INF/fnx.tld");
	// this.addTldResource(context, resource, "META-INF/dw.tld");
	// }
	// }
	//
	// protected boolean isClassesDir(String url) {
	// return url.endsWith("/classes/") || url.endsWith("/classes");
	// }
	//
	// @Override
	// public void preConfigure(final WebAppContext context) throws Exception {
	// this.addFolderResource(context);
	// super.preConfigure(context);
	// }

}
