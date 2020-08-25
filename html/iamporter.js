{
    "status": 200, // HTTP STATUS CODE
    "message": "", // 아임포트 API 응답 메시지 혹은 Iamporter 정의 메시지
    "data": {}, // 아임포트 API 응답 데이터
    "raw": {} // 아임포트 API RAW DATA
  }
  
  iamporter.paySubscription(...)
    .catch((err) => {
      console.log(err.status); // HTTP STATUS CODE
      console.log(err.message); // 아임포트 API 응답 메시지 혹은 Iamporter 정의 메시지
      console.log(err.data); // 아임포트 API 응답 데이터
      console.log(err.raw); // 아임포트 API RAW DATA
    });
  
  
  const { Iamporter, IamporterError } = require("iamporter");
  
  // For Testing (테스트용 API KEY와 SECRET 기본 설정)
  const iamporter = new Iamporter();
  
  // For Production
  const iamporter = new Iamporter({
    apiKey: '7208613406212526',
    secret: 'rBebbodWEfLkp7MsK0yYzQChc1KP1iTsU8E5xS2b7NwwViZFjyDSpfaBgUqidkL8tpe0hgcp4IV01VFl'
  });

  // 인스턴스 생성 시 설정한 API KEY와 SECRET
iamporter.getToken().then(...)

// 토큰 생성 시 사용될 API KEY와 SECRET 직접 지정
iamporter.getToken('687498432154623', 'rVdiEAW40sdCVFdVAXDDFsedfsFEWGAekj0EfdsKJHVvnxpo34VoxxouokPglkcv').then(...)

// Onetime 비인증 결제
iamporter.payOnetime({
    'merchant_uid': 'merchant_1448280088556',
    'amount': 5000,
    'card_number': '1234-1234-1234-1234',
    'expiry': '2021-12',
    'birth': '590912',
    'pwd_2digit': '11'
  }).then(result => {
      console.log(result);
  }).catch(err => {
    if (err instanceof IamporterError)
      // Handle the exception
  });
  
  // 해외카드 비인증 결제
  iamporter.payForeign({
    'merchant_uid': 'merchant_1448280088556',
    'amount': 5000,
    'card_number': '1234-1234-1234-1234',
    'expiry': '2021-12',
  }).then(result => {
      console.log(result);
  }).catch(err => {
    if (err instanceof IamporterError)
      // Handle the exception
  });

  // 아임포트 고유 아이디로 결제 취소
iamporter.cancelByImpUid('imp_448280090638')
.then(...)

// 상점 고유 아이디로 결제 취소
iamporter.cancelByMerchantUid('merchant_1448280088556')
.then(...)

// 상점 고유 아이디로 부분 결제 취소
iamporter.cancelByMerchantUid('merchant_1448280088556', {
'amount': 2500,
'reason': '예약 변경'
}).then(...)

// 결제 취소 후 계좌 환불
iamporter.cancel({
'imp_uid': 'imp_448280090638',
'reason': '제품 상태 불량',
'refund_holder': '홍길동',
'refund_bank': '03',
'refund_account': '056-076923-01-017'
).then(...)

// 아임포트 고유 아이디로 결제정보 조회
iamporter.findByImpUid('imp_448280090638')
  .then(...)

// 상점 고유 아이디로 결제정보 조회
iamporter.findByMerchantUid('merchant_1448280088556')
  .then(...)

// 상점 고유 아이디로 결제정보 목록 조회
iamporter.findAllByMerchantUid('merchant_1448280088556')
  .then(...)

// 결제 상태로 결제정보 목록 조회(status: ['all', 'ready', 'paid', 'cancelled', 'failed'])
iamporter.findAllByStatus('paid')
  .then(...)

  // 결제 예정금액 사전 등록
iamporter.createPreparedPayment({
    'merchant_uid': 'merchant_1448280088556',
    'amount', '128900'
  }).then(...)
  
  // 결제 예정금액 조회
  iamporter.getPreparedPayment('merchant_1448280088556')
    .then(...)

    // 아임포트 고유 아이디로 SMS 본인인증 결과 조회
iamporter.getCertification('imp_448280090638')
.then(...)

// 아임포트 고유 아이디로 SMS 본인인증 결과 삭제
iamporter.deleteCertification('imp_448280090638')
.then(...)

// 가상계좌 발급
iamporter.createVbank({
    'merchant_uid': 'merchant_1448280088556',
    'amount': '128900',
    'vbank_code': '03',
    'vbank_due': 1485697047,
    'vbank_holder': 'PLAT Corp'
  }).then(...)