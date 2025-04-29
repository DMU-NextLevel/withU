import React, { JSX, useState } from "react";
import styled from "styled-components";

interface NewsContentProps {
    title: string;
    content: string;
}

const NewsContent: React.FC<NewsContentProps> = ({ title, content }) => {
    const [isOpen, setIsOpen] = useState<boolean>(false)

    const handleClick = () => {
        setIsOpen(prev => !prev)
    }
    return (
        <NewsWrapper>
            <Title open={isOpen} onClick={handleClick}>{title}</Title>
            {isOpen ? <Content>{content}</Content>: ''}
        </NewsWrapper>
    );
};

export default NewsContent

const NewsWrapper = styled.div`
    display:flex;
    flex-direction:column;
    width: 100%;
    border: 1px solid;
    margin-bottom:10px;
`

const Title = styled.div<{open:boolean}>`
    font-size:20px;
    padding:10px;
    border-bottom: ${({open}) => (open? '1px solid' : 'none')} ;
    background-color: #f3f3f3;
    cursor:pointer;
`

const Content = styled.div`
    font-size:14px;
    padding:10px;
    
`