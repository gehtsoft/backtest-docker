import unittest
import xmlrunner
from indicators_test import *
from datafile_test import *
from strategy_test import *
from bt_test import *
from opt_test import *

if __name__ == "__main__":
    unittest.main(testRunner=xmlrunner.XMLTestRunner(output='test-reports'))
