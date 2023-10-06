package com.teamntp.firstproject.training.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.font.FontProvider;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class ExportPdfServiceImpl implements ExportPdfService {
//주어진 데이터와 템플릿을 사용하여 PDF 문서를 생성하는 역할
//Thymeleaf 템플릿 엔진을 사용하여 HTML 템플릿을 생성하고, 그 결과를 ITextRenderer를 사용하여 PDF로 렌더링하고 출력
    private Logger logger = LoggerFactory.getLogger(ExportPdfServiceImpl.class);

    // final 선언
    private final TemplateEngine templateEngine;

    // TemplateEngine은 Thymeleaf 템플릿 엔진의 주요 인터페이스
    // templateName과 데이터를 이용하여 Thymeleaf의 TemplateEngine을 사용해 HTML 템플릿을 프로세스
    public ByteArrayInputStream exportReceiptPdf(String templateName, Map<String, Object> data) {
        // exportReceiptPdf 메서드에서는 Thymeleaf를 사용하여 템플릿을 HTML로 렌더링하고, 그 HTML을 다시 PDF로 변환하여 ByteArrayInputStream으로 반환
        Context context = new Context();
        context.setVariables(data);

        // templateEngine.process() 메서드를 사용하여 템플릿과 데이터를 결합하여 HTML컨텐츠를 생성
        // 오류 :: org.thymeleaf.exceptions.TemplateInputException 오류는 Thymeleaf 템플릿을 찾거나 파싱하는 과정에서 발생한 문제
        // String htmlContent = templateEngine.process(templateName, context); 충격....
        String htmlContent = templateEngine.process(templateName, context);
        ByteArrayInputStream byteArrayInputStream = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //ITextRenderer 객체를 생성하여 HTML을 PDF로 변환
            ITextRenderer renderer = new ITextRenderer();

            // Load the font  iText 7:: FontProvider
            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider fontProvider = new FontProvider();

            // Path the font
            fontProvider.addFont("C:/work/project/fproject/src/main/resources/font/SongMyung-Regular.ttf");
            //fontProvider.addFont("C:/Users/esnoh/Downloads/230823/fproject/src/main/resources/font/SongMyung-Regular.ttf");

            converterProperties.setFontProvider(fontProvider);

//            // Set A4 paper size
//            PageSize pageSize = new PageSize(PageSize.A4);
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.usePdfAConformance(PdfAConformanceLevel.PDF_A_1B);
//            builder.withHtmlContent(htmlContent, "");
//            builder.toStream(byteArrayOutputStream);
//            builder.run();

            // Convert HTML to PDF
            HtmlConverter.convertToPdf(htmlContent, byteArrayOutputStream, converterProperties);
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        } catch (DocumentException e) {
            logger.error(e.getMessage(), e);
        }

        return byteArrayInputStream;
    }
}