###############CA684 Machine Learning Project###########
###############Tweets sentiment analysis################
#####################Data Cleaning######################


#Note:
#1. In both given training set and train set, each word has a label, e.g."great==A, Galway==^"
#2. This script will only keep the suitable words for this analysis after classify the labels. 
#3. This script 
#End

######################1 Preparation#####################
#For Mac, set the path of data file.
setwd('/Users/xuanyang/Documents/CA684 Machine Learning/SentimentAnalysis')
#Import the training set.
tw_train <- read.csv('./TweetsData/train.csv')
#The main aim is normalise the first column in tw_train, so set it to a new dataset.
tw_train_1 <- tw_train$X.....tfk..N.forum..N......COTY..A.Pool..N.17.........The..D.spinless..A.government..N.propping..V.up..T.cunt....John....Gormley....should..V.get..V.through..P.here..R......URL.Link..V.
gsub(".*==|==.*", "", tw_train_1)

#Partly results:
#[1] "V " "# " "# " "E " ", " "A " "A " "N " ", " "R " "^ " "N " "N "
#[14] "^ " ", " ", " ", " ", " "N " ", " ", " "V " ", " ", " ", " "V "
#[27] "^ " ", " ", " ", " "# " ", " "V " "# " "N " "V " ", " ", " ", "
#[40] ", " ", " ", " "N " "E " "N " "^ " ", " "P " "! " ", " ", " "N "
#[53] "N " "V " "^ " ", " "# " "^ " ", " "N " "E " "# " "O " "# " "# "

#Compare to the contents of training set, conclude:
#1. "V": Verb, keep.
#2. "#": Tags of topic, delete.
#3. "E": Emotion, keep.
#4. ",": Punctuation mark, delete.
#5. "A": Adjective, keep.
#6. "N": Noun, delete
#7. "R": Adjective, keep.
#8. "^": Special name, delete.
#9. "P": Preposition, delete.
#10. "!": Abbreviation of emotion, keep.
#11. "O": Objective, delete.
#12. "@": ID, delete.
#13. "G": Symbol or abbreviation, delete.
#14. "$": Number, delete.
#15. "S": Abbreviation of noun, delete.
#16. "D": Art, delete.
#17. "L": Objective, delete.
#18. "&": And, delete.
#19. "U": You, delete.
#20. "T": Adverb, delete.
#21. "X": Adverb, delete.
#22. "Z": Abbreviation of noun, delete.
####################End 1######################

################2 Keep&Delete labels#################

#Keep:
#"V", "E", "A", "R", "!"

tw_train_r <- gsub("==V", "", tw_train_1)
tw_train_r <- gsub("==E", "", tw_train_r)
tw_train_r <- gsub("==A", "", tw_train_r)
tw_train_r <- gsub("==R", "", tw_train_r)
tw_train_r <- gsub("==!", "", tw_train_r)
tw_train_r 

#Delete:
#",", "#", "N", "^", "P", "O", "@", "G", "$", "S", "D", "L", "&", "U", "T", "Z"

tw_train_r <- gsub("\\S*==, ", "", tw_train_r)
tw_train_r <- gsub("\\S*==# ", "", tw_train_r)
tw_train_r <- gsub("\\S*==N ", "", tw_train_r)
tw_train_r <- gsub("\\S*==\\^ ", "", tw_train_r)
tw_train_r <- gsub("\\S*==P ", "", tw_train_r)
tw_train_r <- gsub("\\S*==O ", "", tw_train_r)
tw_train_r <- gsub("\\S*==@ ", "", tw_train_r)
tw_train_r <- gsub("\\S*==G ", "", tw_train_r)
tw_train_r <- gsub("\\S*==\\$ ", "", tw_train_r)
tw_train_r <- gsub("\\S*==S ", "", tw_train_r)
tw_train_r <- gsub("\\S*==D ", "", tw_train_r)
tw_train_r <- gsub("\\S*==L ", "", tw_train_r)
tw_train_r <- gsub("\\S*==& ", "", tw_train_r)
tw_train_r <- gsub("\\S*==U ", "", tw_train_r)
tw_train_r <- gsub("\\S*==T ", "", tw_train_r)
tw_train_r <- gsub("\\S*==X ", "", tw_train_r)
tw_train_r <- gsub("\\S*==Z ", "", tw_train_r)
tw_train_r
####################End 2#####################

##################3 Normalise###################

#Delete "URL-Link"
tw_train_r <- gsub("URL-Link", "", tw_train_r)
#Delete rest punctuations
tw_train_r <- gsub("[[:punct:]]", "", tw_train_r)
#Delete rest numbers
tw_train_r <- gsub("[[:digit:]]", "", tw_train_r)
#Convert uppercase to lowercase
tw_train_r <- tolower(tw_train_r)
#Replace prepared dataset to the original one
tw_train$X.....tfk..N.forum..N......COTY..A.Pool..N.17.........The..D.spinless..A.government..N.propping..V.up..T.cunt....John....Gormley....should..V.get..V.through..P.here..R......URL.Link..V. <- tw_train_r
#Delete last useless column
tw_train$John.Gormley <- NULL
#Save
write.table(tw_train, file = "./TweetsData/pre_train.csv", row.names = F, col.names = F, sep = ",")
##################End 3##########################