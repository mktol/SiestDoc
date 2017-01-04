package org.siesta.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.siesta.model.Document;
import org.siesta.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CashedDocumentsAspect {

    @Autowired
    private DocumentRepository documentRepository;


    @AfterReturning(
            pointcut = "execution(* org.siesta.service.HandleDocumentService.addDocument(..))",
            returning = "result")
    public void cacheDocument(JoinPoint joinPoint, Object result) {
        Document document = (Document) result;
        documentRepository.save(document);
    }

    @Before("@annotation(org.siesta.aspect.RemoveFromCache)")
    public void removeDocument(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof Document){
                documentRepository.deleteByDocId(((Document)arg).getDocId());
            }
            if(arg instanceof String){
                documentRepository.deleteByDocId((String)arg);
            }
        }
    }

    @Around(value = "execution(* org.siesta.service.HandleDocumentService.getDocumentById(..))")
    public Document getFromCache(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if(arg instanceof String){
                Document document = documentRepository.findDocumentByDocId((String) arg).orElse(null);
                if(document!=null)
                    return document;
            }
        }
        return (Document) pjp.proceed();
    }
}
