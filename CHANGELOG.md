commit 12e6c8a50759a7ba49614be20394c1c974639340
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Fri Apr 30 12:19:30 2021 -0700

    forgot to push driver controller and testing

commit 035223f182ba9410b25a72ac4b38af41fe494854
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Tue Apr 20 11:46:10 2021 -0700

    Added driver crud implementation for service and controller layer. Also created Driver DTO and Mail DTO.

commit adb68146fb067d5af73d2639959e463c68c318cb
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Mon Apr 19 11:20:08 2021 -0700

    Changed userid to username

commit d75598f7034f2cb7affa8f8df8bb065334e56052
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Mon Apr 19 04:56:47 2021 -0700

    all integration testing complete and passed for user crud operations and registration controllers

commit 512b28ca3a688e0efb1372e0a9267dc0864175ce
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Fri Apr 16 11:29:16 2021 -0700

    added admin controller

commit a0d204584feac1ffffaf448b3a4909929b31bab4
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Thu Apr 15 11:33:42 2021 -0700

    Modded uri and return types to fit expectations and added implementation for the user crud controller

commit 289d9a07042bb713860bec69250c7d3fde057873
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Tue Apr 13 11:00:45 2021 -0700

    Finished service layer test implementation and passed all cases. Will implement controller and integration testing next.

commit 1191a7837b4ac0dac1615fa4f0d353abc602b8b7
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Tue Apr 13 10:16:19 2021 -0700

    Change password encryption to hashing and implemented methods for user update, get user info, and close/reactivate account. Testing and controller methods to be added.

commit 8b47232cae018687ece4df2524855a9cf6a4d0b2
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Thu Apr 8 05:47:22 2021 -0700

    completed unit testing. Reached 80 percent coverage. Realized too late that JWT and spring security is the way to go. Will fix in future.

commit 84d81cf4559f431656665e12e834b17bc71d0579
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Thu Apr 8 03:59:22 2021 -0700

    updated url paths to match expected uses and added verification entity and refactored everything using verification code from user to the newer entity

commit 20d84771e4f7b72124282ec3bb9828addeb533f7
Merge: 2655797 065af75
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Wed Apr 7 14:43:37 2021 -0700

    Merge pull request #9 from xSushiRollerx/user_authorization_Dylan
    
    Forgot to test the sessions

commit 065af755b4cf3822bf89e69047d50354d68cd496
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Wed Apr 7 14:42:33 2021 -0700

    Forgot to test the sessions

commit 26557971022596d64f1d4924883704a20172fe33
Merge: 243da0d 3f02bcf
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Wed Apr 7 13:47:28 2021 -0700

    Merge pull request #8 from xSushiRollerx/user_authorization_Dylan
    
    User authorization dylan

commit 3f02bcfa17affdcc2cba265794a13cb6d42cadfe
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Wed Apr 7 13:46:04 2021 -0700

    added controller test and service test for authorization, login and logout stories

commit eee1ba4d095201b25c453f60ee12409071f44464
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Mon Apr 5 19:45:38 2021 -0700

    Added implementation for login and logout as well as session timeout

commit 243da0d4ca4220e19a1ab2cdd48ce0c3f4cc2400
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Mon Apr 5 14:23:23 2021 -0700

    got deletion of unverified users completed.

commit aea4d2b9917226a81ea2a3129b049ccd6a62a5ef
Merge: 5c994c6 9937af5
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Fri Apr 2 11:38:42 2021 -0700

    Merge pull request #5 from xSushiRollerx/user_registration_Dylan
    
    User registration dylan

commit 9937af526af8a722d42ae9ae6801c5384517d685
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Fri Apr 2 11:37:29 2021 -0700

    finished integration testing

commit 64897b25ed7011d918de59c2ad35c7cd297b7096
Author: damocles-coder <dylan.tran@smoothstack.com>
Date:   Thu Apr 1 14:41:07 2021 -0700

    added node js code to use mailer module for verification

commit 5c994c67014fa5748476514d6e909ca6e93328c2
Merge: 6eaff87 dad52fd
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Thu Apr 1 12:21:48 2021 -0400

    Merge pull request #4 from xSushiRollerx/user_registration_Dylan
    
    User registration dylan

commit dad52fdcbd05b0609f891ab1429a697c2370a15c
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Thu Apr 1 08:44:37 2021 -0700

    user registration works fully. Validation is implemented, but currently not working with a temporary email.

commit b760eb93b5731ceb4c8673d5e1663608d700bc97
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Wed Mar 31 16:15:26 2021 -0700

    fixed url mapping (not tested yet)

commit 9b2f7ae414f4062b041945bc37d232b97ff34f87
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Wed Mar 31 15:50:15 2021 -0700

    started implementation of verification of email. Heavily relies on example. Will utilize java mail api to send the email instead.

commit f794f705cfd2d9c6bdaf53dbf6831c7e345452dc
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Wed Mar 31 15:01:43 2021 -0700

    added user registration service and mockito test, will add restcontroller later

commit 5c70c9b017ba6f5208b14de87b58fa76a58137fa
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Mon Mar 29 19:32:03 2021 -0700

    Adding DAO, entities, and DAO tests

commit 6eaff87651ffd807471bd3ec3c48a82766cd6a5f
Merge: 22e78b9 9f35b4f
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Fri Mar 26 18:09:30 2021 -0400

    Merge pull request #1 from xSushiRollerx/dev
    
    Dev

commit 9f35b4f4099a9d5308246d2608edf72819838747
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Fri Mar 26 15:05:10 2021 -0700

    Forgot to eclipsify project

commit c0d1de187a5a9aa598caaa6a256cf79b1d6e2509
Author: Damocles-coder <35568707+dyltra@users.noreply.github.com>
Date:   Thu Mar 25 11:20:43 2021 -0700

    added initial spring boot project

commit 22e78b9b3eff59b14bcd2b1c86a98e80440a9eef
Author: Damocles-coder <80295448+Damocles-coder@users.noreply.github.com>
Date:   Thu Mar 25 13:25:46 2021 -0400

    Initial commit
