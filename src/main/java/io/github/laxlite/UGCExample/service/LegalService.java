package io.github.laxlite.UGCExample.service;

import io.github.laxlite.UGCExample.dto.LegalSection;
import io.github.laxlite.UGCExample.dto.LegalSectionShort;
import io.github.laxlite.UGCExample.exception.PageNotFoundException;
import io.github.laxlite.UGCExample.model.LegalDocument;
import io.github.laxlite.UGCExample.repository.LegalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalService {
    private final LegalRepository legalRepository;

    public LegalSection getSectionBySlug(String slug){
        LegalDocument document = legalRepository.findBySlug(slug).orElseThrow(PageNotFoundException::new);
        return new LegalSection(slug, document.getHeader(), document.getHtml());
    }

    // TODO: возможно, тащить целый html из БД для каждого документа не оптимально
    public List<LegalSectionShort> getAllSections(){
        return legalRepository.findAll()
                .stream()
                .map(
                        legalDocument -> new LegalSectionShort(
                                legalDocument.getSlug(),
                                legalDocument.getHeader()
                        )
                )
                .toList();
    }

    @Autowired
    public LegalService(LegalRepository legalRepository){
        this.legalRepository = legalRepository;
    }
}
