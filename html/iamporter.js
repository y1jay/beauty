var Iamport = require("iamport");
var iamport = new Iamport({
  impKey: "7208613406212526",
  impSecret:
    "rBebbodWEfLkp7MsK0yYzQChc1KP1iTsU8E5xS2b7NwwViZFjyDSpfaBgUqidkL8tpe0hgcp4IV01VFl",
});

// 아임포트 고유 아이디로 결제 정보를 조회
iamport.payment
  .getByImpUid({
    imp_uid: "imp73346050",
  })
  .then(function (result) {
    // To do
  })
  .catch(function (error) {
    // handle error
  });

// 상점 고유 아이디로 결제 정보를 조회
iamport.payment.getByMerchant({
  merchant_uid: "your merchant_uid",
});

// 상태별 결제 정보 조회
iamport.payment.getByStatus({
  payment_status: "your payment_status",
});
