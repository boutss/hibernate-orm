package org.hibernate.boot.archive.scan.internal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.Set;

import org.hibernate.boot.archive.internal.ByteArrayInputStreamAccess;
import org.hibernate.boot.archive.internal.FileInputStreamAccess;
import org.hibernate.boot.archive.internal.UrlInputStreamAccess;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;
import org.hibernate.boot.archive.scan.spi.PackageDescriptor;

import org.hibernate.testing.TestForIssue;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the serialization of {@link ScanResultImpl}.
 */
public class ScanResultImplTest {


	@Test
	@TestForIssue(jiraKey = "HHH-XXX")
	public void to_be_able_to_serialize_scanresultimpl() {

		ScanResultImpl scanResult = new ScanResultImpl(
				getSetOfPackageDescriptor(),
				Set.of( new ClassDescriptorImpl(
						"name",
						ClassDescriptor.Categorization.CONVERTER,
						new FileInputStreamAccess( "filename", new File( "." ) )
				) ),
				Set.of( new MappingFileDescriptorImpl( "name", new UrlInputStreamAccess( null ) ) )
		);

		try (ByteArrayOutputStream output = new ByteArrayOutputStream(); ObjectOutputStream s = new ObjectOutputStream(
				output )) {
			s.writeObject( scanResult );
			s.flush();
		}
		catch (Throwable e) {
			Assert.fail( "Should not have an Throwable here : " + e.getMessage() );
		}
	}

	private Set<PackageDescriptor> getSetOfPackageDescriptor() {
		PackageDescriptorImpl packageDescriptorWithByteArray = new PackageDescriptorImpl(
				"ByteArrayInputStreamAccess",
				new ByteArrayInputStreamAccess(
						null,
						null
				)
		);
		PackageDescriptorImpl packageDescriptorWithFileInput = new PackageDescriptorImpl(
				"FileInputStreamAccess",
				new FileInputStreamAccess(
						"filename",
						new File( "." )
				)
		);
		PackageDescriptorImpl packageDescriptorWithUrlInput = new PackageDescriptorImpl(
				"UrlInputStreamAccess",
				new UrlInputStreamAccess( null )
		);

		return Set.of(
				packageDescriptorWithByteArray,
				packageDescriptorWithFileInput,
				packageDescriptorWithUrlInput
		);
	}
}