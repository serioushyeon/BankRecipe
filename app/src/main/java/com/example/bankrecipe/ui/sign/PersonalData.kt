package com.example.bankrecipe.ui.sign

data class PersonalData( var userUid : String? = null,
                         var ID : String? =null,
                         var Password : String? = null,
                         var UserName: String? = null,  //or representationName
                         var Email : String? = null, // or tax
                         var businessNumber : String? = null,
                         var corporationName : String? = null,
                         var corporationLocation : String? = null,
                         var businessType : String? = null,
                         var userType : String? = null //seller or user
                        )
