package ge.propertygeorgia.catalogue.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HeaderService {

    private final HeaderRepository headerRepository;

    @Autowired
    public HeaderService(HeaderRepository headerRepository) {

        this.headerRepository = headerRepository;
    }

    public Header getHeader() {
        if (headerRepository.existsById(1l)) {
            return headerRepository.findById(1l).get();
        }
        return null;
    }


    public void postHeader(Header header) {
        System.out.println("Header Service");
        headerRepository.save(header);
    }

    @Transactional
    public void updateHeader(String description, String descriptionEng, String[] images, String[] files) {
        if (headerRepository.existsById(1l)) {
            Header header = headerRepository.findById(1l)
                    .orElse(null);
            if (description != null) header.setDescription(description);
            if (descriptionEng != null) header.setDescriptionEng(descriptionEng);
            if (images != null) header.setImages(images);
            if (files != null) header.setFiles(files);
        }
    }
}
