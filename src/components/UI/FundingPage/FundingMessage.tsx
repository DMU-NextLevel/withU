import React from 'react'
import styled from 'styled-components'

interface ChatMessageProps {
  isSender: boolean // true면 보낸 사람(왼쪽), false면 받은 사람(오른쪽)
  userName: string
  date: string
  message: string
}

const FundingMessage: React.FC<ChatMessageProps> = ({ isSender, userName, date, message }) => {
  return (
    <MessageWrapper isSender={isSender}>
      <ProfileCircle isSender={isSender} />
      <ContentWrapper isSender={isSender}>
        <MessageBubble isSender={isSender}>
          {message}
        </MessageBubble>
        <UserInfo>
          <UserName>{userName}</UserName>
          <DateText>{date}</DateText>
        </UserInfo>
      </ContentWrapper>
    </MessageWrapper>
  )
}

export default FundingMessage

const MessageWrapper = styled.div<{ isSender: boolean }>`
  display: flex;
  flex-direction: ${({ isSender }) => (isSender ? 'row' : 'row-reverse')};
  align-items: flex-end;
  margin: 20px 0;
`

const ProfileCircle = styled.div<{ isSender: boolean }>`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: ${({ isSender }) => (isSender ? '#a99eff' : '#d9d9d9')};
  flex-shrink: 0;
`

const ContentWrapper = styled.div<{ isSender: boolean }>`
  display: flex;
  flex-direction: column;
  align-items: ${({ isSender }) => (isSender ? 'flex-start' : 'flex-end')};
  margin: 0 10px;
`

const MessageBubble = styled.div<{ isSender: boolean }>`
  max-width: 20vw;
  padding: 15px 20px;
  border: 4px solid ${({ isSender }) => (isSender ? '#a99eff' : '#d9d9d9')};
  border-radius: 20px;
  background-color: white;
  position: relative;
  font-size: 16px;
  line-height: 1.5;
  word-break: break-word;
  }
`

const UserInfo = styled.div`
  margin-top: 8px;
`

const UserName = styled.div`
  font-weight: bold;
  font-size: 14px;
  color: #222;
`

const DateText = styled.div`
  font-size: 12px;
  color: #888;
`
