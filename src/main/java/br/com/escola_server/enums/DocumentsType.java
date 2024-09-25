package br.com.escola_server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
;

@AllArgsConstructor
@Getter
public enum DocumentsType {
    IDENTIFICACAO(1,"Identificacao"),
    ENDERECO(2,"Endereco"),
    UNIAO_CIVIL(3,"União Civil"),
    CPF(4,"CPF"),
    CNPJ(5,"CNPJ");

    private final String descricao;
    private final int valor;

    DocumentsType(int valor, String descricao) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getValor(){
        return valor;
    }

}

