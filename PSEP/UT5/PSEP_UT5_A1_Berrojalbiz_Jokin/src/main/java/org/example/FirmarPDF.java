package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;



public class FirmarPDF {

    private PrivateKey privateKey;
    private X509Certificate cert;

    // Constructor que recibe los parámetros necesarios para firmar
    public FirmarPDF(String keystorePath, char[] password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(Files.newInputStream(Paths.get(keystorePath)), password);
        this.privateKey = (PrivateKey) ks.getKey(ks.aliases().nextElement(), password);
        this.cert = (X509Certificate) ks.getCertificate(ks.aliases().nextElement());
    }

    // Método principal
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ruta del PDF a firmar: ");
        String pdfPath = sc.nextLine().trim();
        System.out.print("Ruta del almacén (.p12/.pfx): ");
        String keystorePath = sc.nextLine().trim();
        System.out.print("Contraseña del almacén: ");
        char[] password = sc.nextLine().toCharArray();

        FirmarPDF app = new FirmarPDF(keystorePath, password);

        // Definir los parámetros de la firma
        String outputPath = pdfPath.replace(".pdf", "_signed.pdf");
        String signerName = "Firma Digital";
        String signerLocation = "Ubicación";
        String signerReason = "Razón de la firma";

        app.firmarPDF(pdfPath, outputPath, signerName, signerLocation, signerReason);
    }

    // Método para firmar el PDF
    public void firmarPDF(String inputPdfPath, String outputPdfPath, String name, String location, String reason) throws Exception {
        PDDocument doc = PDDocument.load(new File(inputPdfPath));

        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        signature.setName(name);
        signature.setLocation(location);
        signature.setReason(reason);
        signature.setSignDate(Calendar.getInstance());

        doc.addSignature(signature, this::sign); // Se firma el PDF con el método sign

        try (OutputStream os = Files.newOutputStream(Paths.get(outputPdfPath))) {
            doc.saveIncremental(os);
        }

        doc.close();
        System.out.println("PDF firmado y guardado en: " + outputPdfPath);
    }

    // Método para crear la firma digital PKCS7
    public byte[] sign(InputStream content) throws IOException {
        try {
            byte[] data = content.readAllBytes();

            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

            ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider("BC")
                    .build(privateKey);

            gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build()).build(signer, (X509Certificate) cert));

            gen.addCertificates(new JcaCertStore(Collections.singletonList(cert)));

            CMSProcessableByteArray msg = new CMSProcessableByteArray(data);
            CMSSignedData signedData = gen.generate(msg, false); // detached signature
            return signedData.getEncoded();

        } catch (Exception e) {
            throw new IOException("Error generando la firma digital: " + e.getMessage(), e);
        }
    }
}
