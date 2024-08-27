package com.example.dmoney.navigation.route

sealed class AuthRoute (
    val route:String,
    val title:String){

    data object Home: AuthRoute(
        route = "Home",
        title = "Home"
    )

    data object Login: AuthRoute(
        route = "Login",
        title = "Login"
    )

    data object SignUp: AuthRoute(
        route = "SignUp",
        title = "SignUp"
    )
    data object OtpVerification: AuthRoute(
        route = "Otp",
        title = "Otp"
    )

    data object MyQR: AuthRoute(
        route = "MyQR",
        title = "MyQR"
    )

    data object SetPIN: AuthRoute(
        route = "SetPIN",
        title = "SetPIN"
    )

    data object AddReferral: AuthRoute(
        route = "AddReferral",
        title = "AddReferral"
    )

    data object SetEmail: AuthRoute(
        route = "SetEmail",
        title = "SetEmail"
    )


    data object AccountType: AuthRoute(
        route = "AccountType",
        title = "AccountType"
    )

    data object EKYCType: AuthRoute(
        route = "EKYCType",
        title = "EKYCType"
    )

    data object NIDVerification: AuthRoute(
        route = "NIDVerification",
        title = "NIDVerification"
    )
    data object NIDScanning: AuthRoute(
        route = "NIDScanning",
        title = "NIDScanning"
    )

    data object NIDScanData: AuthRoute(
        route = "NIDScanData",
        title = "NIDScanData"
    )

    data object SelfieVerification: AuthRoute(
        route = "SelfieVerification",
        title = "SelfieVerification"
    )

    data object AdditionalInformation: AuthRoute(
        route = "AdditionalInformation",
        title = "AdditionalInformation"
    )
  data object EkycSuccessScreen: AuthRoute(
        route = "EkycSuccessScreen",
        title = "EkycSuccessScreen"
    )

    data object WalletCreationSuccessScreen: AuthRoute(
        route = "WalletCreationSuccessScreen",
        title = "WalletCreationSuccessScreen"
    )

    data object EkycFailedScreen: AuthRoute(
        route = "EkycFailedScreen",
        title = "EkycFailedScreen"
    )

    data object FaceAnalyzer: AuthRoute(
        route = "FaceAnalyzer",
        title = "FaceAnalyzer"
    )

    data object BirthCertificateScreen: AuthRoute(
        route = "BirthCertificateScreen",
        title = "BirthCertificateScreen"
    )

    data object BirthCertificateScanDataScreen: AuthRoute(
        route = "BirthCertificateScanDataScreen",
        title = "BirthCertificateScanDataScreen"
    )

    data object GuardianVerification: AuthRoute(
        route = "GuardianVerification",
        title = "GuardianVerification"
    )
    data object EkycMinorSuccessScreen: AuthRoute(
        route = "EkycMinorSuccessScreen",
        title = "EkycMinorSuccessScreen"
    )

    data object GuardianRequestScreen: AuthRoute(
        route = "GuardianRequestScreen",
        title = "GuardianRequestScreen"
    )

    data object PassportInformationScreen: AuthRoute(
        route = "PassportInformationScreen",
        title = "PassportInformationScreen"
    )



    data object PassportScanDataScreen: AuthRoute(
        route = "PassportScanDataScreen",
        title = "PassportScanDataScreen"
    )


}
