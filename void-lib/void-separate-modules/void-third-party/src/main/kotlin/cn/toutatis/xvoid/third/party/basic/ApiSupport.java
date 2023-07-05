package cn.toutatis.xvoid.third.party.basic;

public interface ApiSupport {

    public default ApiDocumentInfo resolve(String name){
        return DocumentMappingEnumPropertyUtils.resolve(name);
    }

}
